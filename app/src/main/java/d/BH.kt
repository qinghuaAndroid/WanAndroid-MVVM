package d

import o.CG
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

class BH @Inject constructor(private val apiService: ApiService): BaseRepository() {

    suspend fun getUserInfo(): Result<CG> {
        return safeApiCall { requestUserInfo() }
    }

    private suspend fun requestUserInfo(): Result<CG> {
        return executeResponse(apiService.getUserInfo())
    }
}