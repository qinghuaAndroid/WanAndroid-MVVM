package k

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class BS @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun getSystemArticle(pageNum: Int, cid: Int): Result<BY> {
        return safeApiCall { requestSystemArticle(pageNum, cid) }
    }

    private suspend fun requestSystemArticle(pageNum: Int, cid: Int): Result<BY> {
        return executeResponse(apiService.getSystemArticle(pageNum, cid))
    }
}