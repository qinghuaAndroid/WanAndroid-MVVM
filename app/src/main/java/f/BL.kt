package f

import o.BY
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class BL @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getQuestionList(pageNum: Int): Result<BY> {
        return safeApiCall { requestQuestionList(pageNum) }
    }

    private suspend fun requestQuestionList(pageNum: Int): Result<BY> {
        return executeResponse(apiService.getQuestionList(pageNum))
    }
}