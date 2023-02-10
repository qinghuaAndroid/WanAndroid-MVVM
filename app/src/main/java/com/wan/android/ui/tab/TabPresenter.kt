package com.wan.android.ui.tab

import com.wan.baselib.mvp.BasePresenter
import com.wan.baselib.network.RxHelper
import com.wan.baselib.network.RxObserver
import com.wan.android.bean.TabEntity

/**
 * @author cy
 * Create at 2020/4/3.
 */
class TabPresenter : BasePresenter<TabContract.Model, TabContract.View>(), TabContract.Presenter {

    override fun createModel(): TabContract.Model = TabModel()

    override fun loadData(type: Int) {
        mView?.showLoading()
        val disposableObserver =
            mModel?.loadData(type)?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<MutableList<TabEntity>>() {
                override fun onSuccess(t: MutableList<TabEntity>?) {
                    mView?.hideLoading()
                    t?.let { mView?.showList(it) }
                }

                override fun onFailed(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    mView?.showError(errorMsg)
                }
            })
        addDisposable(disposableObserver)
    }
}