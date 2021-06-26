package com.wan.android.ui.tab

import com.wan.baselib.mvp.IModel
import com.wan.baselib.mvp.IPresenter
import com.wan.baselib.mvp.IView
import com.wan.baselib.network.HttpResult
import com.wan.android.bean.TabEntity
import io.reactivex.rxjava3.core.Observable


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