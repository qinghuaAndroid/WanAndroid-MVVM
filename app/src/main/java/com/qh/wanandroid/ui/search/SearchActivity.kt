package com.qh.wanandroid.ui.search

import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.devlibrary.ext.listener.queryTextListener
import com.example.devlibrary.helper.RealmHelper
import com.example.devlibrary.mvp.BaseMvpActivity
import com.qh.wanandroid.R
import com.qh.wanandroid.arouter.ArouterPath
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.bean.SearchHistoryBean
import com.qh.wanandroid.const.Const
import com.qh.wanandroid.databinding.ActivitySearchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/4/14.
 */
@Route(path = ArouterPath.ACTIVITY_SEARCH)
class SearchActivity :
    BaseMvpActivity<SearchContract.View, SearchContract.Presenter, ActivitySearchBinding>(),
    SearchContract.View {

    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_search

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setTitle(R.string.search)
        initListener()
    }

    private fun initListener() {
        mBinding.ivClearAll.onClick {
            mPresenter?.clearAllHistory()
            mBinding.historyLabelsView.removeAllViews()
        }
    }

    private fun goToSearchList(key: String) {
        mPresenter?.saveSearchKey(key)
        ARouter.getInstance().build(ArouterPath.ACTIVITY_SEARCH_LIST)
            .withString(Const.SEARCH_KEY, key).navigation()
    }

    override fun loadData() {
        mPresenter?.getHotSearchData()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.queryHistory()
    }

    override fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>) {
        mBinding.historyLabelsView.run {
            setLabels(historyBeans) { _, _, data ->
                data.key
            }
            setOnLabelClickListener { _, _, position ->
                onLabelClick(historyBeans[position].key)
            }
        }
    }

    override fun showHotSearchData(hotSearchDatas: MutableList<HotSearchEntity>) {
        mBinding.hotLabelsView.run {
            setLabels(hotSearchDatas) { _, _, data ->
                data.name
            }
            setOnLabelClickListener { _, _, position ->
                onLabelClick(hotSearchDatas[position].name)
            }
        }
    }

    private fun onLabelClick(queryTxt: String) {
        goToSearchList(queryTxt)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.queryTextListener {
            onQueryTextSubmit { query ->
                query?.let { goToSearchList(it) }
            }
        }
        searchView.isSubmitButtonEnabled = true
        initStyle(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initStyle(searchView: SearchView) {
        val goButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_go_btn)
        goButton.setImageResource(R.drawable.ic_search_white_24dp)
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmHelper.realm.close()
    }
}