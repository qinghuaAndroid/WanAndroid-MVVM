package l

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/16.
 */
class BT @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun getProjectList(pageNum: Int, id: Int): Result<BY> {
        return safeApiCall { requestProjectList(pageNum, id) }
    }

    private suspend fun requestProjectList(pageNum: Int, id: Int): Result<BY> {
        return executeResponse(apiService.getProjectList(pageNum, id))
    }

    suspend fun getAccountList(id: Int, pageNum: Int): Result<BY> {
        return safeApiCall { requestAccountList(id, pageNum) }
    }

    private suspend fun requestAccountList(id: Int, pageNum: Int): Result<BY> {
        return executeResponse(apiService.getAccountList(id, pageNum))
    }
}