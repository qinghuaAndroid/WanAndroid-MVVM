package m

import o.BY
import o.BZ
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/16.
 */
class BU @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun loadTopArticles(): Result<MutableList<BY.DatasBean>> {
        return safeApiCall { requestTopArticles() }
    }

    suspend fun loadArticles(pageNum: Int): Result<BY> {
        return safeApiCall { requestArticles(pageNum) }
    }

    suspend fun loadBanner(): Result<MutableList<BZ>> {
        return safeApiCall { requestBanner() }
    }

    private suspend fun requestTopArticles(): Result<MutableList<BY.DatasBean>> {
        return executeResponse(apiService.getTopList())
    }

    private suspend fun requestArticles(pageNum: Int): Result<BY> {
        return executeResponse(apiService.getHomeList(pageNum))
    }

    private suspend fun requestBanner(): Result<MutableList<BZ>> {
        return executeResponse(apiService.getBanner())
    }
}