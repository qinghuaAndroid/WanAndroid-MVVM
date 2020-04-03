package com.qh.wanandroid.ui.home

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class HomeModel:BaseModel(),HomeContract.Model {

    override fun loadTopArticles(): Observable<HttpResult<MutableList<ArticleEntity.DatasBean>>> {
        return HttpHelper.apiService.getTopList()
    }

    override fun loadArticles(pageNum: Int): Observable<HttpResult<ArticleEntity>> {
        return HttpHelper.apiService.getHomeList(pageNum)
    }

    override fun loadBanner(): Observable<HttpResult<MutableList<BannerEntity>>> {
        return HttpHelper.apiService.getBanner()
    }

    override fun collect(id: Int): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.collect(id)
    }

    override fun unCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.unCollect(id)
    }
}