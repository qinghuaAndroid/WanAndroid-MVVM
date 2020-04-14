package com.qh.wanandroid.ui.search

import android.content.Intent
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.devlibrary.ext.listener.queryTextListener
import com.example.devlibrary.mvp.BaseMvpActivity
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.SearchHistoryAdapter
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.bean.SearchHistoryBean
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.databinding.ActivitySearchBinding
import com.qh.wanandroid.ui.search.list.SearchListActivity
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/4/14.
 */
class SearchActivity :
    BaseMvpActivity<SearchContract.View, SearchContract.Presenter, ActivitySearchBinding>(),
    SearchContract.View {

    private val searchHistoryAdapter by lazy { SearchHistoryAdapter() }

    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_search

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setTitle(R.string.search)
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        mBinding.tvClearAll.onClick {
            mPresenter?.clearAllHistory()
            searchHistoryAdapter.data.clear()
            searchHistoryAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        mBinding.rvHistorySearch.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchHistoryAdapter
            itemAnimator = DefaultItemAnimator()
        }
        searchHistoryAdapter.run {
            setOnItemClickListener(mOnItemClickListener)
            addChildClickViewIds(R.id.iv_clear)
            setOnItemChildClickListener(mOnItemChildClickListener)
        }
    }

    private val mOnItemClickListener = OnItemClickListener { _, _, position ->
        goToSearchList(searchHistoryAdapter.data[position].key)
    }

    private val mOnItemChildClickListener =
        OnItemChildClickListener { _, view, position ->
            if (view.id == R.id.iv_clear) {
                mPresenter?.deleteByKey(searchHistoryAdapter.data[position].key)
            }
        }

    private fun goToSearchList(key: String) {
        mPresenter?.saveSearchKey(key)
        Intent(this, SearchListActivity::class.java).run {
            putExtra(Const.SEARCH_KEY, key)
            startActivity(this)
        }
    }

    override fun loadData() {
        mPresenter?.getHotSearchData()
    }

    override fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>) {
        searchHistoryAdapter.setList(historyBeans)
    }

    override fun showHotSearchData(hotSearchDatas: MutableList<HotSearchEntity>) {
        mBinding.hotLabelsView.run {
            setLabels(hotSearchDatas) { _, _, data ->
                data.name
            }
            setOnLabelClickListener { _, _, position ->
                onLabelClick(hotSearchDatas[position])
            }
        }
    }

    private fun onLabelClick(hotSearchEntity: HotSearchEntity) {
        goToSearchList(hotSearchEntity.name)
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
}