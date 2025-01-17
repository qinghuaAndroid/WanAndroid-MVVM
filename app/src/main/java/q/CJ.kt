package q

import o.Q
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class CJ @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getSystemList(): Result<MutableList<Q>> {
        return safeApiCall { requestSystemList() }
    }

    private suspend fun requestSystemList(): Result<MutableList<Q>> {
        return executeResponse(apiService.getSystemList())
    }
}