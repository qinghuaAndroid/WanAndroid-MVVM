package com.wan.android.ui.home

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.ArticleEntity
import com.wan.android.bean.BannerEntity
import com.wan.android.http.HttpHelper

/**
 * @author cy
 * Create at 2020/4/16.
 */
class HomeRepository: BaseRepository() {

    suspend fun loadTopArticles(): Result<MutableList<ArticleEntity.DatasBean>> {
        return safeApiCall { requestTopArticles() }
    }

    suspend fun loadArticles(pageNum: Int): Result<ArticleEntity> {
        return safeApiCall { requestArticles(pageNum) }
    }

    suspend fun loadBanner(): Result<MutableList<BannerEntity>> {
        return safeApiCall { requestBanner() }
    }

    private suspend fun requestTopArticles(): Result<MutableList<ArticleEntity.DatasBean>> {
        return executeResponse(HttpHelper.apiService.getTopList())
    }

    private suspend fun requestArticles(pageNum: Int): Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getHomeList(pageNum))
    }

    private suspend fun requestBanner(): Result<MutableList<BannerEntity>> {
        return executeResponse(HttpHelper.apiService.getBanner())
    }
}