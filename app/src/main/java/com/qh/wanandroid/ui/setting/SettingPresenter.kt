package com.qh.wanandroid.ui.setting

import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.RxHelper
import com.example.devlibrary.network.RxObserver

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class SettingPresenter : BasePresenter<SettingContract.Model, SettingContract.View>(),
    SettingContract.Presenter {

    override fun createModel(): SettingContract.Model? {
        return SettingModel()
    }

    override fun logout() {
        mView?.showLoading()
        val disposableObserver = mModel?.logout()?.compose(RxHelper.handleResult())
            ?.subscribeWith(object : RxObserver<Any>() {
                override fun onSuccess(t: Any?) {
                    mView?.hideLoading()
                    mView?.logoutSuccess()
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.hideLoading()
                    mView?.showError(errorMsg)
                }
            })
        addDisposable(disposableObserver)
    }
}