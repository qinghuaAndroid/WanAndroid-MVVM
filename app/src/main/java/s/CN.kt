package s

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class CN @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun queryBySearchKey(pageNum: Int, key: String): Result<BY> {
        return safeApiCall { requestSearchArticle(pageNum, key) }
    }

    private suspend fun requestSearchArticle(pageNum: Int, key: String): Result<BY> {
        return executeResponse(apiService.queryBySearchKey(pageNum, key))
    }
}