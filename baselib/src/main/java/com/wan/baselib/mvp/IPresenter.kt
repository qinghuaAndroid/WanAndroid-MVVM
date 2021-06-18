package com.wan.baselib.mvp

/**
 * Created by chenxz on 2018/4/21.
 */
interface IPresenter<in V : IView> {

    /**
     * 绑定 View
     */
    fun attachView(view: V)

    /**
     * 解绑 View
     */
    fun detachView()

}