package com.qh.wanandroid.ui.search

import com.example.devlibrary.helper.RealmHelper
import com.example.devlibrary.mvp.BasePresenter
import com.example.devlibrary.network.RxHelper
import com.example.devlibrary.network.RxObserver
import com.example.devlibrary.utils.isNull
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.bean.SearchHistoryBean


class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(),
    SearchContract.Presenter {

    private val realm by lazy { RealmHelper.realm }

    override fun createModel(): SearchContract.Model? = SearchModel()

    override fun deleteByKey(key: String) {
        val results = realm.where(SearchHistoryBean::class.java).equalTo("key", key).findAll()
        realm.executeTransaction {
            results.deleteAllFromRealm()
        }
    }

    override fun clearAllHistory() {
        val results = realm.where(SearchHistoryBean::class.java).findAll()
        realm.executeTransaction {
            results.deleteAllFromRealm()
        }
    }

    override fun saveSearchKey(key: String) {
        val result = realm.where(SearchHistoryBean::class.java).equalTo("key", key).findFirst()
        result.isNull<SearchHistoryBean> {
            realm.executeTransactionAsync {
                val searchHistoryBean = it.createObject(SearchHistoryBean::class.java)
                searchHistoryBean.key = key
            }
        }
    }

    override fun queryHistory() {
        val results = realm.where(SearchHistoryBean::class.java).findAll()
        val historyBeans: MutableList<SearchHistoryBean> = mutableListOf()
        for (searchHistoryBean in results) {
            historyBeans.add(searchHistoryBean)
        }
        mView?.showHistoryData(historyBeans)
    }

    override fun getHotSearchData() {
        val disposableObserver =
            mModel?.getHotSearchData()?.compose(RxHelper.handleResult())?.subscribeWith(object :
                RxObserver<MutableList<HotSearchEntity>>() {
                override fun onSuccess(t: MutableList<HotSearchEntity>?) {
                    t?.let { mView?.showHotSearchData(it) }
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {
                    mView?.showError(errorMsg)
                }
            })
        addDisposable(disposableObserver)
    }

}