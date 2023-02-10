package com.wan.android.ui.search

import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import com.wan.baselib.mvp.BasePresenter
import com.wan.baselib.network.RxHelper
import com.wan.baselib.network.RxObserver
import io.realm.OrderedCollectionChangeSet
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.RealmResults


class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(),
    SearchContract.Presenter {

    override fun createModel(): SearchContract.Model = SearchModel()

    override fun deleteByKey(key: String) {
        mModel?.deleteByKey(key)
    }

    override fun clearAllHistory() {

        mModel?.clearAllHistory()
    }

    override fun saveSearchKey(key: String) {
        mModel?.saveSearchKey(key)
    }

    override fun addChangeListener() {
        mModel?.getRealmResults()?.addChangeListener(orderedRealmCollectionChangeListener)
    }

    private val orderedRealmCollectionChangeListener =
        OrderedRealmCollectionChangeListener<RealmResults<SearchHistoryBean>> { collection, changeSet ->
            if (changeSet.state == OrderedCollectionChangeSet.State.INITIAL
                || changeSet.state == OrderedCollectionChangeSet.State.UPDATE
            ) {
                mView?.showHistoryData(collection)
            }
        }

    override fun getHotSearchData() {
        val disposableObserver =
            mModel?.getHotSearchData()?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<MutableList<HotSearchEntity>>() {
                override fun onSuccess(t: MutableList<HotSearchEntity>?) {
                    t?.let { mView?.showHotSearchData(it) }
                }

                override fun onFailed(errorCode: Int, errorMsg: String?) {
                    mView?.showError(errorMsg)
                }
            })
        addDisposable(disposableObserver)
    }

    override fun removeChangeListener() {
        mModel?.getRealmResults()?.removeChangeListener(orderedRealmCollectionChangeListener)
    }

}