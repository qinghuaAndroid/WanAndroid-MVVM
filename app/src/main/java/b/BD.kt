package b

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/16.
 */
class BD @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun getCollectData(pageNum: Int): Result<BY> {
        return safeApiCall { requestCollectData(pageNum) }
    }

    private suspend fun requestCollectData(pageNum: Int): Result<BY> {
        return executeResponse(apiService.getCollectData(pageNum))
    }

    suspend fun collect(id: Int): Result<Any> {
        return safeApiCall { requestCollect(id) }
    }

    private suspend fun requestCollect(id: Int): Result<Any> {
        return executeResponse(apiService.collect(id))
    }

    suspend fun unCollect(id: Int): Result<Any> {
        return safeApiCall { requestCancelCollect(id) }
    }

    private suspend fun requestCancelCollect(id: Int): Result<Any> {
        return executeResponse(apiService.unCollect(id))
    }

    suspend fun unMyCollect(id: Int, originId: Int): Result<Any> {
        return safeApiCall { requestCancelMyCollect(id, originId) }
    }

    private suspend fun requestCancelMyCollect(id: Int, originId: Int): Result<Any> {
        return executeResponse(apiService.unMyCollect(id, originId))
    }
}