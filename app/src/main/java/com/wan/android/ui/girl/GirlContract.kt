package com.wan.android.ui.girl

import com.wan.baselib.mvp.IModel
import com.wan.baselib.mvp.IPresenter
import com.wan.baselib.mvp.IView
import com.wan.android.bean.GankIoDataBean
import io.reactivex.rxjava3.core.Observable

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