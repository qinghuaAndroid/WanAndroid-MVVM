package com.wan.android.ui.myarticle

import com.wan.android.bean.MyArticleEntity
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class MyArticleRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getMyArticle(pageNum: Int): Result<MyArticleEntity> {
        return safeApiCall { requestMyArticle(pageNum) }
    }

    private suspend fun requestMyArticle(pageNum: Int): Result<MyArticleEntity> {
        return executeResponse(apiService.getMyArticle(pageNum))
    }
}