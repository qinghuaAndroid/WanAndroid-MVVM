package com.qh.wanandroid.ui.tab.list

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.ArticleEntity
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/3.
 */
interface TabListContract {

    interface View : IView {
        fun showList(list: MutableList<ArticleEntity.DatasBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun loadData(type: Int, id: Int, pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface Model : IModel {
        fun loadData(type: Int, id: Int, pageNum: Int): Observable<HttpResult<ArticleEntity>>
        fun collect(id: Int): Observable<HttpResult<Any>>
        fun unCollect(id: Int): Observable<HttpResult<Any>>
    }
}