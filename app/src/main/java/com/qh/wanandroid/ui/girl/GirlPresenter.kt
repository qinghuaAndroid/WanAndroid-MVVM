package com.qh.wanandroid.ui.girl

import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.SchedulerUtils
import com.qh.wanandroid.bean.GankIoDataBean
import io.reactivex.observers.DisposableObserver

class GirlPresenter : BasePresenter<GirlContract.Model, GirlContract.View>(),
    GirlContract.Presenter {
    override fun createModel(): GirlContract.Model? {
        return GirlModel()
    }

    override fun requestMeiziList(type: String, limit: Int, page: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.requestMeiziList(type, limit, page)?.compose(SchedulerUtils.ioToMain())
                ?.subscribeWith(object : DisposableObserver<GankIoDataBean>() {
                    override fun onComplete() {
                        mView?.hideLoading()
                    }

                    override fun onNext(t: GankIoDataBean) {
                        if (t.isError.not()) {
                            mView?.setMeiziList(t.results)
                        }
                    }

                    override fun onError(e: Throwable) {
                        mView?.hideLoading()
                        e.message?.let { mView?.loadMeiziListFail(it) }
                        e.message?.let { mView?.showError(it) }
                    }

                })
        addDisposable(disposableObserver)
    }
}