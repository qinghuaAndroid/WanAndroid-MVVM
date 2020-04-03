package com.qh.wanandroid.ui.tab

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.TabEntity
import io.reactivex.Observable


/**
 * @author FQH
 * Create at 2020/4/3.
 */
interface TabContract {
    interface View : IView {
        fun showList(list: MutableList<TabEntity>)
    }

    interface Presenter : IPresenter<View> {
        fun loadData(type: Int)
    }

    interface Model : IModel {
        fun loadData(type: Int): Observable<HttpResult<MutableList<TabEntity>>>
    }
}