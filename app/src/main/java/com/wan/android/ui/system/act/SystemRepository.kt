package com.wan.android.ui.system.act

import com.wan.android.bean.ArticleEntity
import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class SystemRepository @Inject constructor() : BaseRepository() {

    suspend fun getSystemArticle(pageNum: Int, cid: Int): Result<ArticleEntity> {
        return safeApiCall { requestSystemArticle(pageNum, cid) }
    }

    private suspend fun requestSystemArticle(pageNum: Int, cid: Int): Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getSystemArticle(pageNum, cid))
    }
}