package com.wan.android.ui.share

import com.wan.android.bean.ArticleEntity
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class ShareListRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getShareArticle(pageNum: Int): Result<ArticleEntity> {
        return safeApiCall { requestShareArticle(pageNum) }
    }

    private suspend fun requestShareArticle(pageNum: Int): Result<ArticleEntity> {
        return executeResponse(apiService.getShareArticle(pageNum))
    }
}