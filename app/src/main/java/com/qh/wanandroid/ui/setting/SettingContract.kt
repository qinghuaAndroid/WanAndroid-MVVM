package com.qh.wanandroid.ui.setting

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import io.reactivex.Observable

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