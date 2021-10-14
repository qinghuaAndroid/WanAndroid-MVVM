package com.wan.android.ui.search

import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wan.baselib.ext.listener.queryTextListener
import com.wan.baselib.helper.RealmHelper
import com.wan.baselib.mvp.BaseMvpActivity
import com.wan.android.R
import com.wan.common.arouter.ArouterPath
import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import com.wan.android.constant.Const
import com.wan.android.databinding.ActivitySearchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author cy
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
        mPresenter?.addChangeListener()
        mBinding.ivClearAll.onClick {
            mPresenter?.clearAllHistory()
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
        mPresenter?.removeChangeListener()
        RealmHelper.realm.close()
    }
}