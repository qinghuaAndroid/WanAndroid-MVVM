package com.qh.wanandroid.ui.collect

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.zs.wanandroid.entity.CollectEntity
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/13.
 */
interface CollectContract {

    interface View : IView {
        fun showList(collectEntity: CollectEntity)
        fun loadDataFail()
        fun cancelCollectSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun loadData(page: Int)
        fun cancelCollect(id: Int)
    }

    interface Model : IModel {
        fun loadData(page: Int): Observable<HttpResult<CollectEntity>>
        fun cancelCollect(id: Int): Observable<HttpResult<Any>>
    }
}