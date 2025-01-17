package e

import o.CC
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class BJ @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getMyArticle(pageNum: Int): Result<CC> {
        return safeApiCall { requestMyArticle(pageNum) }
    }

    private suspend fun requestMyArticle(pageNum: Int): Result<CC> {
        return executeResponse(apiService.getMyArticle(pageNum))
    }
}