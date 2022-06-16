package com.wan.android.ui.share

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.ArticleEntity
import com.wan.android.http.HttpHelper
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class ShareListRepository @Inject constructor() : BaseRepository() {

    suspend fun getShareArticle(pageNum:Int):Result<ArticleEntity> {
        return safeApiCall { requestShareArticle(pageNum) }
    }

    private suspend fun requestShareArticle(pageNum: Int):Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getShareArticle(pageNum))
    }
}