package com.qh.wanandroid.ui.share

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class ShareListRepository : BaseRepository() {

    suspend fun getShareArticle(pageNum:Int):Result<ArticleEntity> {
        return safeApiCall { requestShareArticle(pageNum) }
    }

    private suspend fun requestShareArticle(pageNum: Int):Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getShareArticle(pageNum))
    }
}