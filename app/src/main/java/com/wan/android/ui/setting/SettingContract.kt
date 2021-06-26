package com.wan.android.ui.setting

import com.wan.baselib.mvp.IModel
import com.wan.baselib.mvp.IPresenter
import com.wan.baselib.mvp.IView
import com.wan.baselib.network.HttpResult
import io.reactivex.rxjava3.core.Observable

/**
 * @author FQH
 * Create at 2020/4/10.
 */
interface SettingContract {

    interface View : IView {
        fun logoutSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun logout()
    }

    interface Model : IModel {
        fun logout(): Observable<HttpResult<Any>>
    }
}