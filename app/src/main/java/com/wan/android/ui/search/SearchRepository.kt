package com.wan.android.ui.search

import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    private val realm: Realm

    init {
        val configuration = RealmConfiguration.create(schema = setOf(SearchHistoryBean::class))
        realm = Realm.open(configuration)
    }

    fun queryAll(): Flow<ResultsChange<SearchHistoryBean>> {
        return realm.query<SearchHistoryBean>().find().asFlow()
    }

    suspend fun saveSearchKey(key: String) {
        val historyBean = realm.query<SearchHistoryBean>("key == $0", key).first().find()
        if (historyBean == null) {
            val searchHistoryBean = SearchHistoryBean().apply { this.key = key }
            realm.write { copyToRealm(searchHistoryBean) }
        }
    }

    fun deleteByKey(key: String) {
        realm.writeBlocking {
            val query = query<SearchHistoryBean>("key == $0", key)
            delete(query)
        }
    }

    fun clearAll() {
        realm.writeBlocking {
            val results = query<SearchHistoryBean>().find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }

    suspend fun getHotSearchData(): Result<MutableList<HotSearchEntity>> {
        return safeApiCall { requestHotSearchData() }
    }

    private suspend fun requestHotSearchData(): Result<MutableList<HotSearchEntity>> {
        return executeResponse(apiService.getHotSearchData())
    }
}