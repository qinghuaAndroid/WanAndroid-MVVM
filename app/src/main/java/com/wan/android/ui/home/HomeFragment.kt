package com.wan.android.ui.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.appbar.AppBarLayout
import com.wan.android.R
import com.wan.android.adapter.ArticleAdapter
import com.wan.android.adapter.ImageNetAdapter
import com.wan.android.databinding.FragmentHomeBinding
import com.wan.android.ui.ArticleViewModel
import com.wan.android.ui.collect.CollectViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.baselib.widget.LoadMoreView
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author cy
 * Create at 2020/4/2.
 */
class HomeFragment : BaseVMFragment<ArticleViewModel, FragmentHomeBinding>() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val collectViewModel by viewModel<CollectViewModel>()
    private val articleAdapter by lazy { ArticleAdapter() }
    private var curPosition = 0

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initData() {

    }

    override fun initView(view: View) {
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
        mBinding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                mBinding.swipeRefresh.isEnabled = verticalOffset >= 0
            })
    }

    private fun initRecyclerView() {
        mBinding.rvHomeList.layoutManager = LinearLayoutManager(context)
        mBinding.rvHomeList.adapter = articleAdapter
        articleAdapter.loadMoreModule.loadMoreView = LoadMoreView()
        articleAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        articleAdapter.loadMoreModule.isAutoLoadMore = true
        articleAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        articleAdapter.setOnItemClickListener(mOnItemClickListener)
        articleAdapter.addChildClickViewIds(R.id.ivCollect)
        articleAdapter.setOnItemChildClickListener(mOnItemChildClickListener)
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
        homeViewModel.getBanner()
        homeViewModel.getTopArticles()
    }

    private fun loadMore() {
        articleViewModel.getArticles(false)
    }

    override fun onStart() {
        super.onStart()
        mBinding.banner.start()
    }

    override fun onStop() {
        super.onStop()
        mBinding.banner.stop()
    }

    override fun startObserve() {
        homeViewModel.bannerUiState.observe(this, Observer {
            it.showSuccess?.let { list ->
                mBinding.banner.adapter = ImageNetAdapter(list)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        })
        homeViewModel.topArticleUiState.observe(this, Observer {
            it.showSuccess?.let { list ->
                articleAdapter.setList(list)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        })
        articleViewModel.uiState.observe(this, Observer {
            mBinding.swipeRefresh.isRefreshing = it.showLoading
            it.showSuccess?.let { articleEntity ->
                articleEntity.datas?.let { list ->
                    if (it.isRefresh) articleAdapter.addData(list)
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
}