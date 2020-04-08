package com.qh.wanandroid.ui.tab

import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.RxHelper
import com.example.devlibrary.network.RxObserver
import com.qh.wanandroid.bean.ArticleEntity

/**
 * @author FQH
 * Create at 2020/4/3.
 */
class TabListPresenter : BasePresenter<TabListContract.Model, TabListContract.View>(),
    TabListContract.Presenter {

    override fun createModel(): TabListContract.Model? {
        return TabListModel()
    }

    override fun loadData(type: Int, id: Int, pageNum: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadData(type, id, pageNum)?.compose(RxHelper.handleResult())
                ?.subscribeWith(object :
                    RxObserver<ArticleEntity>() {
                    override fun onSuccess(t: ArticleEntity?) {
                        mView?.hideLoading()
                        t?.datas?.let { mView?.showList(it) }
                    }

                    override fun onFail(errorCode: Int, errorMsg: String?) {
                        mView?.hideLoading()
                        errorMsg?.let { mView?.showError(it) }
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
                    mView?.collectSuccess()
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
                    mView?.unCollectSuccess()
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    errorMsg?.let { mView?.showMsg(it) }
                }
            })
        addDisposable(disposableObserver)
    }
}