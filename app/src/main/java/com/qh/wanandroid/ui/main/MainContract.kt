package com.qh.wanandroid.ui.main

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.UserInfoEntity
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/20.
 */
interface MainContract {

    interface View : IView {
        fun showLogoutSuccess(success: Boolean)
        fun showUserInfo(bean: UserInfoEntity)
    }

    interface Presenter : IPresenter<View> {
        fun logout()
        fun getUserInfo()
    }

    interface Model : IModel {
        fun logout(): Observable<HttpResult<Any>>
        fun getUserInfo(): Observable<HttpResult<UserInfoEntity>>
    }
}