package com.qh.wanandroid.ui.home

import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.RxHelper
import com.example.devlibrary.network.RxObserver
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(),
    HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? {
        return HomeModel()
    }

    override fun loadTopArticles() {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadTopArticles()?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<MutableList<ArticleEntity.DatasBean>>() {
                override fun onSuccess(t: MutableList<ArticleEntity.DatasBean>?) {
                    mView?.hideLoading()
                    t?.let { mView?.showTopArticlesList(it) }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showError(it) }
                }
            })
        addDisposable(disposableObserver)
    }

    override fun loadArticles(pageNum: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadArticles(pageNum)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<ArticleEntity>() {
                override fun onSuccess(t: ArticleEntity?) {
                    mView?.hideLoading()
                    t?.datas?.let { mView?.showArticlesList(it) }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showError(it) }
                }
            })
        addDisposable(disposableObserver)
    }

    override fun loadBanner() {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadBanner()?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<MutableList<BannerEntity>>() {
                override fun onSuccess(t: MutableList<BannerEntity>?) {
                    mView?.hideLoading()
                    t?.let { mView?.showBanner(it) }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showMsg(it) }
                }
            })
        addDisposable(disposableObserver)
    }

    override fun collect(id: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.collect(id)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<Any>() {
                override fun onSuccess(t: Any?) {
                    mView?.hideLoading()
                    t?.let { mView?.collectSuccess() }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showMsg(it) }
                }
            })
        addDisposable(disposableObserver)
    }

    override fun unCollect(id: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.unCollect(id)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<Any>() {
                override fun onSuccess(t: Any?) {
                    mView?.hideLoading()
                    t?.let { mView?.unCollectSuccess() }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showMsg(it) }
                }
            })
        addDisposable(disposableObserver)
    }
}