package com.qh.wanandroid.ui.girl

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.qh.wanandroid.bean.GankIoDataBean
import io.reactivex.Observable

interface GirlContract {

    interface View : IView {

        fun setMeiziList(resultBeanList: List<GankIoDataBean.ResultBean>)
        fun loadMeiziListFail(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        fun requestMeiziList(category: String, type: String, count: Int, page: Int)

    }

    interface Model : IModel {

        fun requestMeiziList(category: String, type: String, count: Int, page: Int): Observable<GankIoDataBean>

    }
}