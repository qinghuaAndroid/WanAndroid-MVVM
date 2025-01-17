package g

import o.CA
import o.CE
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BM @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    private val realm: Realm

    init {
        val configuration = RealmConfiguration.create(schema = setOf(CE::class))
        realm = Realm.open(configuration)
    }

    fun queryAll(): Flow<ResultsChange<CE>> {
        return realm.query<CE>().find().asFlow()
    }

    suspend fun saveSearchKey(key: String) {
        val historyBean = realm.query<CE>("key == $0", key).first().find()
        if (historyBean == null) {
            val searchHistoryBean = CE().apply { this.key = key }
            realm.write { copyToRealm(searchHistoryBean) }
        }
    }

    fun deleteByKey(key: String) {
        realm.writeBlocking {
            val query = query<CE>("key == $0", key)
            delete(query)
        }
    }

    fun clearAll() {
        realm.writeBlocking {
            val results = query<CE>().find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }

    suspend fun getHotSearchData(): Result<MutableList<CA>> {
        return safeApiCall { requestHotSearchData() }
    }

    private suspend fun requestHotSearchData(): Result<MutableList<CA>> {
        return executeResponse(apiService.getHotSearchData())
    }
}