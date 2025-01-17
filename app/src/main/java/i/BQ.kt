package i

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class BQ @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getShareArticle(pageNum: Int): Result<BY> {
        return safeApiCall { requestShareArticle(pageNum) }
    }

    private suspend fun requestShareArticle(pageNum: Int): Result<BY> {
        return executeResponse(apiService.getShareArticle(pageNum))
    }
}