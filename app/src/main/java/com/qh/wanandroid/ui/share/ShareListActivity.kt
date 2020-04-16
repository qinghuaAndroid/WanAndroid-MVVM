package com.qh.wanandroid.ui.share

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMActivity
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.databinding.ActivityShareListBinding
import com.qh.wanandroid.ui.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class ShareListActivity : BaseVMActivity<ArticleViewModel, ActivityShareListBinding>() {

    private val viewModel by viewModel<ArticleViewModel>()
    private val articleAdapter by lazy { ArticleAdapter() }

    override fun startObserve() {
        viewModel.uiState.observe(this, Observer {
            mBinding.swipeRefresh.isRefreshing = it.showLoading
            it.showSuccess?.let { articleEntity ->
                articleEntity.datas?.let { list ->
                    if (it.isRefresh) articleAdapter.setList(list)
                    else articleAdapter.addData(list)
                }
                articleAdapter.loadMoreModule.loadMoreComplete()
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
                articleAdapter.loadMoreModule.loadMoreFail()
            }
            if (it.showEnd) articleAdapter.loadMoreModule.loadMoreEnd()
            articleAdapter.loadMoreModule.isEnableLoadMore = it.isEnableLoadMore
        })
    }

    override fun attachLayoutRes(): Int = R.layout.activity_share_list

    override fun initData() {

    }

    override fun initView() {
        title = getString(R.string.my_article)
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        mBinding.rvShareList.run {
            layoutManager = LinearLayoutManager(this@ShareListActivity)
            adapter = articleAdapter
        }
        articleAdapter.run {
            loadMoreModule.loadMoreView = LoadMoreView()
            loadMoreModule.setOnLoadMoreListener { loadMore() }
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        }
    }

    override fun loadData() {
        viewModel.getShareArticle(true)
    }

    private fun loadMore() {
        viewModel.getShareArticle(false)
    }
}