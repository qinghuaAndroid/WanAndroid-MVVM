package c

import o.CB
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/10.
 */
class BF @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getIntegralRecord(pageNum: Int): Result<CB> {
        return safeApiCall { requestIntegralRecord(pageNum) }
    }

    private suspend fun requestIntegralRecord(pageNum: Int): Result<CB> {
        return executeResponse(apiService.getIntegralRecord(pageNum))
    }
}