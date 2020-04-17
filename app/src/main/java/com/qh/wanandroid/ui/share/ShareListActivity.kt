package com.qh.wanandroid.ui.share

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.common.constant.Const
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMActivity
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.databinding.ActivityShareListBinding
import com.qh.wanandroid.ui.ArticleViewModel
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.qh.wanandroid.ui.collect.CollectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class ShareListActivity : BaseVMActivity<ArticleViewModel, ActivityShareListBinding>() {

    private val articleViewModel by viewModel<ArticleViewModel>()
    private val collectViewModel by viewModel<CollectViewModel>()
    private val articleAdapter by lazy { ArticleAdapter() }
    private var curPosition = 0

    override fun startObserve() {
        articleViewModel.uiState.observe(this, Observer {
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
        collectViewModel.uiState.observe(this, Observer {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { collect ->
                articleAdapter.data[curPosition].collect = collect
                articleAdapter.notifyItemChanged(curPosition)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
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
            setOnItemClickListener(mOnItemClickListener)
            addChildClickViewIds(R.id.ivCollect)
            setOnItemChildClickListener(mOnItemChildClickListener)
        }
    }

    private val mOnItemClickListener = OnItemClickListener { _, _, position ->
        val datasBean = articleAdapter.data[position]
            Intent(this, BrowserNormalActivity::class.java).run {
                putExtra(Const.WEB_TITLE, datasBean.title)
                putExtra(Const.WEB_URL, datasBean.link)
                startActivity(this)
        }
    }

    private val mOnItemChildClickListener = OnItemChildClickListener { _, view, position ->
        val datasBean = articleAdapter.data[position]
        curPosition = position
        when (view.id) {
            R.id.ivCollect -> {
                if (datasBean.collect) collectViewModel.unCollect(datasBean.id)
                else collectViewModel.collect(datasBean.id)
            }
        }
    }

    override fun loadData() {
        articleViewModel.getShareArticle(true)
    }

    private fun loadMore() {
        articleViewModel.getShareArticle(false)
    }
}