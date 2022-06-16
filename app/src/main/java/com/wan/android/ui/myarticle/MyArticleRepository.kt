package com.wan.android.ui.myarticle

import com.wan.android.bean.MyArticleEntity
import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class MyArticleRepository @Inject constructor() : BaseRepository() {

    suspend fun getMyArticle(pageNum: Int): Result<MyArticleEntity> {
        return safeApiCall { requestMyArticle(pageNum) }
    }

    private suspend fun requestMyArticle(pageNum: Int): Result<MyArticleEntity> {
        return executeResponse(HttpHelper.apiService.getMyArticle(pageNum))
    }
}