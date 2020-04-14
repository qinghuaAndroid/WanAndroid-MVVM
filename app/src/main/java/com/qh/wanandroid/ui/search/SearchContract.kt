package com.qh.wanandroid.ui.search

import com.example.devlibrary.mvp.IModel
import com.example.devlibrary.mvp.IPresenter
import com.example.devlibrary.mvp.IView
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.bean.SearchHistoryBean
import io.reactivex.Observable

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