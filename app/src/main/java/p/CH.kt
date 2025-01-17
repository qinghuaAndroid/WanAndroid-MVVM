package p

import o.O
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class CH @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getNavigation(): Result<MutableList<O>> {
        return safeApiCall { requestNavigation() }
    }

    private suspend fun requestNavigation(): Result<MutableList<O>> {
        return executeResponse(apiService.getNavigation())
    }
}