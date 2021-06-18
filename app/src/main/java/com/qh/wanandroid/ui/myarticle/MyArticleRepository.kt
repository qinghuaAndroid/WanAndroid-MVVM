package com.qh.wanandroid.ui.myarticle

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.qh.wanandroid.bean.MyArticleEntity
import com.qh.wanandroid.http.HttpHelper

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