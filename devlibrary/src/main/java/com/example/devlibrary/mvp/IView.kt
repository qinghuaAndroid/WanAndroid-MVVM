package com.example.devlibrary.mvp

/**
 * Created by chenxz on 2018/4/21.
 */
interface IView {

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示信息
     */
    fun showMsg(msg: String)

    /**
     * 显示错误信息
     */
    fun showError(errorCode:Int, errorMsg: String?)

}