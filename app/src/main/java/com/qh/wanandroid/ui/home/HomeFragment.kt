package com.qh.wanandroid.ui.home

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.common.constant.Const
import com.example.devlibrary.ext.getThemeColor
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvvm.BaseVMFragment
import com.example.devlibrary.utils.DisplayUtils
import com.example.devlibrary.utils.StatusBarUtil
import com.example.devlibrary.widget.LoadMoreView
import com.google.android.material.appbar.AppBarLayout
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.adapter.ImageNetAdapter
import com.qh.wanandroid.databinding.FragmentHomeBinding
import com.qh.wanandroid.ui.ArticleViewModel
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.qh.wanandroid.ui.collect.CollectViewModel
import com.qh.wanandroid.ui.search.SearchActivity
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class HomeFragment :
    BaseVMFragment<ArticleViewModel, FragmentHomeBinding>(){

    private val homeViewModel by viewModel<HomeViewModel>()
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val collectViewModel by viewModel<CollectViewModel>()
    private var criticalValue: Int = 0
    private val articleAdapter by lazy { ArticleAdapter() }
    private var curPosition = 0

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initData() {
        val statusBarHeight = StatusBarUtil.getStatusBarHeight(context)
        criticalValue = DisplayUtils.dp2px(215F) - statusBarHeight
        receiveNotice()
    }

    override fun initView(view: View) {
        setThemeColor()
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
        StatusBarUtil.setPaddingTop(context, mBinding.rlSearch)
        mBinding.ivSearch.onClick { startActivity<SearchActivity>() }
        mBinding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                mBinding.swipeRefresh.isEnabled = verticalOffset >= 0
                if (verticalOffset > -criticalValue) {
                    val rate = kotlin.math.abs(verticalOffset) / criticalValue.toFloat()
                    mBinding.rlSearch.alpha = rate
                } else {
                    mBinding.rlSearch.alpha = 1.0F
                }
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
        activity?.let {
            Intent(it, BrowserNormalActivity::class.java).run {
                putExtra(Const.WEB_TITLE, datasBean.title)
                putExtra(Const.WEB_URL, datasBean.link)
                it.startActivity(this)
            }
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

    private fun receiveNotice() {
        LiveEventBusHelper.observe(com.example.common.constant.Const.THEME_COLOR,
            Int::class.java, this, Observer<Int> {
                setThemeColor()
            })
    }

    private fun setThemeColor() {
        mBinding.rlSearch.backgroundColor = getThemeColor()
    }

    override fun startObserve() {
        homeViewModel.bannerUiState.observe(this, Observer {
            it.showSuccess?.let {list ->
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