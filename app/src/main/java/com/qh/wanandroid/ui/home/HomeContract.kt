package com.qh.wanandroid.ui.home

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/2.
 */
interface HomeContract {

    interface View : IView {
        fun showTopArticlesList(list: MutableList<ArticleEntity.DatasBean>)
        fun showArticlesList(articleEntity: ArticleEntity)
        fun showBanner(bannerList: MutableList<BannerEntity>)
        fun loadArticlesFail()
        fun collectSuccess()
        fun unCollectSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun loadTopArticles()
        fun loadArticles(pageNum: Int)
        fun loadBanner()
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface Model : IModel {
        fun loadTopArticles(): Observable<HttpResult<MutableList<ArticleEntity.DatasBean>>>
        fun loadArticles(pageNum: Int): Observable<HttpResult<ArticleEntity>>
        fun loadBanner(): Observable<HttpResult<MutableList<BannerEntity>>>
        fun collect(id: Int): Observable<HttpResult<Any>>
        fun unCollect(id: Int): Observable<HttpResult<Any>>
    }
}