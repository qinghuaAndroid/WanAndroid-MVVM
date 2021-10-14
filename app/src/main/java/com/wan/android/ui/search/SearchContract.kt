package com.wan.android.ui.search

import com.wan.baselib.mvp.IModel
import com.wan.baselib.mvp.IPresenter
import com.wan.baselib.mvp.IView
import com.wan.baselib.network.HttpResult
import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmResults

interface SearchContract {

    interface View : IView {

        fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>)

        fun showHotSearchData(hotSearchDatas: MutableList<HotSearchEntity>)

    }

    interface Presenter : IPresenter<View> {

        fun addChangeListener()

        fun saveSearchKey(key: String)

        fun deleteByKey(key: String)

        fun clearAllHistory()

        fun getHotSearchData()

        fun removeChangeListener()
    }

    interface Model : IModel {

        fun getRealmResults(): RealmResults<SearchHistoryBean>

        fun saveSearchKey(key: String)

        fun deleteByKey(key: String)

        fun clearAllHistory()

        fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>>

    }

}