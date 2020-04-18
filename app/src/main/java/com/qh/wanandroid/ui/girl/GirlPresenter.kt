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

    override fun requestMeiziList(category: String, type: String, count: Int, page: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.requestMeiziList(category, type, count, page)?.compose(SchedulerUtils.ioToMain())
                ?.subscribeWith(object : DisposableObserver<GankIoDataBean>() {
                    override fun onComplete() {
                        mView?.hideLoading()
                    }

                    override fun onNext(t: GankIoDataBean) {
                        if (t.status == 100) {
                            mView?.setMeiziList(t.data)
                        }
                    }

                    override fun onError(e: Throwable) {
                        mView?.hideLoading()
                        e.message?.let { mView?.loadMeiziListFail(it) }
                    }

                })
        addDisposable(disposableObserver)
    }
}