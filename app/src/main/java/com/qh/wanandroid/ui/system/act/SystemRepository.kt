package com.qh.wanandroid.ui.system.act

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemRepository : BaseRepository() {

    suspend fun getSystemArticle(pageNum: Int, cid: Int): Result<ArticleEntity> {
        return safeApiCall { requestSystemArticle(pageNum, cid) }
    }

    private suspend fun requestSystemArticle(pageNum: Int, cid: Int): Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getSystemArticle(pageNum, cid))
    }
}