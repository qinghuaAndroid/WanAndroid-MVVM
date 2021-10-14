package com.wan.android.ui.search

import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import com.wan.android.http.HttpHelper
import com.wan.baselib.helper.RealmHelper
import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.wan.baselib.utils.isNull
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmResults

class SearchModel : BaseModel(), SearchContract.Model {

    private val realm by lazy { RealmHelper.realm }

    private val results: RealmResults<SearchHistoryBean>? = null

    @Synchronized
    override fun getRealmResults(): RealmResults<SearchHistoryBean> {
        return results ?: realm.where(SearchHistoryBean::class.java).findAllAsync()
    }

    override fun saveSearchKey(key: String) {
        realm.executeTransactionAsync {
            val result = it.where(SearchHistoryBean::class.java).equalTo("key", key).findFirst()
            result.isNull<SearchHistoryBean> {
                val searchHistoryBean = it.createObject(SearchHistoryBean::class.java)
                searchHistoryBean.key = key
            }
        }
    }

    override fun deleteByKey(key: String) {
        realm.executeTransactionAsync {
            val results = it.where(SearchHistoryBean::class.java).equalTo("key", key).findAll()
            results.deleteAllFromRealm()
        }
    }

    override fun clearAllHistory() {
        realm.executeTransactionAsync {
            val results = it.where(SearchHistoryBean::class.java).findAll()
            results.deleteAllFromRealm()
        }
    }

    override fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>> {
        return HttpHelper.apiService.getHotSearchData()
    }

}