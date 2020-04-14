package com.qh.wanandroid.ui.search.list

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.http.HttpHelper

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