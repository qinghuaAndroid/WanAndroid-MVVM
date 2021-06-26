package com.wan.android.ui.search.list

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.ArticleEntity
import com.wan.android.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SearchListRepository : BaseRepository() {

    suspend fun queryBySearchKey(pageNum: Int, key: String): Result<ArticleEntity> {
        return safeApiCall { requestSearchArticle(pageNum, key) }
    }

    private suspend fun requestSearchArticle(pageNum: Int, key: String): Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.queryBySearchKey(pageNum, key))
    }
}