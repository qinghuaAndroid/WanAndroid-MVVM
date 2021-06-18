package com.qh.wanandroid.ui.search

import com.wan.baselib.mvp.IModel
import com.wan.baselib.mvp.IPresenter
import com.wan.baselib.mvp.IView
import com.wan.baselib.network.HttpResult
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.bean.SearchHistoryBean
import io.reactivex.rxjava3.core.Observable

interface SearchContract {

    interface View : IView {

        fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>)

        fun showHotSearchData(hotSearchDatas: MutableList<HotSearchEntity>)

    }

    interface Presenter : IPresenter<View> {

        fun queryHistory()

        fun saveSearchKey(key: String)

        fun deleteByKey(key: String)

        fun clearAllHistory()

        fun getHotSearchData()

    }

    interface Model : IModel {

        fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>>

    }

}