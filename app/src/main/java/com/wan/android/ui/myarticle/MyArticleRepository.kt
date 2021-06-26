package com.wan.android.ui.myarticle

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.MyArticleEntity
import com.wan.android.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class MyArticleRepository : BaseRepository() {

    suspend fun getMyArticle(pageNum:Int):Result<MyArticleEntity> {
        return safeApiCall { requestMyArticle(pageNum) }
    }

    private suspend fun requestMyArticle(pageNum: Int):Result<MyArticleEntity> {
        return executeResponse(HttpHelper.apiService.getMyArticle(pageNum))
    }
}