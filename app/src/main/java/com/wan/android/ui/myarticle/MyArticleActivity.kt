package com.wan.android.ui.myarticle

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.wan.android.R
import com.wan.android.adapter.ArticleAdapter
import com.wan.android.databinding.ActivityMyArticleBinding
import com.wan.android.ui.collect.CollectViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.widget.LoadMoreView
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author cy
 * Create at 2020/4/15.
 */
@AndroidEntryPoint
class MyArticleActivity : BaseVMActivity<MyArticleViewModel, ActivityMyArticleBinding>() {

    private val articleViewModel by viewModels<MyArticleViewModel>()
    private val collectViewModel by viewModels<CollectViewModel>()
    private val articleAdapter by lazy { ArticleAdapter() }
    private var curPosition = 0

    override fun subscribeUi() {
        articleViewModel.uiState.observe(this) {
            binding.swipeRefresh.isRefreshing = it.showLoading
            it.showSuccess?.let { myArticleEntity ->
                myArticleEntity.shareArticles?.datas?.let { list ->
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
        }
        collectViewModel.uiState.observe(this) {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { collect ->
                articleAdapter.data[curPosition].collect = collect
                articleAdapter.notifyItemChanged(curPosition)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_my_article

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        title = getString(R.string.my_article)
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        binding.rvMyArticle.run {
            layoutManager = LinearLayoutManager(this@MyArticleActivity)
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
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(Const.WEB_TITLE, datasBean.title)
            .withString(Const.WEB_URL, datasBean.link)
            .navigation()
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
        articleViewModel.getMyArticle(true)
    }

    private fun loadMore() {
        articleViewModel.getMyArticle(false)
    }
}