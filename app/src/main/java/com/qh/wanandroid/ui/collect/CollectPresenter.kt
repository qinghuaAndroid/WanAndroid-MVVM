package com.qh.wanandroid.ui.collect

import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.RxHelper
import com.example.devlibrary.network.RxObserver
import com.zs.wanandroid.entity.CollectEntity

/**
 * @author FQH
 * Create at 2020/4/13.
 */
class CollectPresenter : BasePresenter<CollectContract.Model, CollectContract.View>(),
    CollectContract.Presenter {

    override fun createModel(): CollectContract.Model? {
        return CollectModel()
    }

    override fun loadData(page: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadData(page)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<CollectEntity>() {
                override fun onSuccess(t: CollectEntity?) {
                    mView?.hideLoading()
                    t?.let { mView?.showList(it) }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    mView?.showError(errorMsg)
                    mView?.loadDataFail()
                }
            })
        addDisposable(disposableObserver)
    }

    override fun cancelCollect(id: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.cancelCollect(id)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<Any>() {
                override fun onSuccess(t: Any?) {
                    mView?.hideLoading()
                    mView?.cancelCollectSuccess()
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    mView?.showError(errorMsg)
                }
            })
        addDisposable(disposableObserver)
    }
}