package com.qh.wanandroid.ui.home

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.constant.Const
import com.example.devlibrary.mvp.BaseMvpFragment
import com.example.devlibrary.widget.LoadMoreView
import com.google.android.material.appbar.AppBarLayout
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.adapter.ImageNetAdapter
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity
import com.qh.wanandroid.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class HomeFragment :
    BaseMvpFragment<HomeContract.View, HomeContract.Presenter, FragmentHomeBinding>(),
    HomeContract.View {

    private val articleList: MutableList<ArticleEntity.DatasBean> = mutableListOf()
    private val articleAdapter by lazy { ArticleAdapter(articleList) }
    private var isRefresh = false
    private var pageNum = 0

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initData() {

    }

    override fun initView(view: View) {
        super.initView(view)
        initRecyclerView()
        swipeRefresh.setOnRefreshListener { loadData() }
        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                swipeRefresh.isEnabled = verticalOffset >= 0
            })
    }

    private fun initRecyclerView() {
        rvHomeList.layoutManager = LinearLayoutManager(context)
        rvHomeList.adapter = articleAdapter
        articleAdapter.loadMoreModule.loadMoreView = LoadMoreView()
        articleAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        articleAdapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        articleAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }

    override fun loadData() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        articleAdapter.loadMoreModule.isEnableLoadMore = false
        // 下拉刷新，需要重置页数
        pageNum = 0
        isRefresh = true
        mPresenter?.loadBanner()
        mPresenter?.loadTopArticles()
    }

    private fun loadMore() {
        ++pageNum
        isRefresh = false
        mPresenter?.loadArticles(pageNum)
    }

    override fun showTopArticlesList(list: MutableList<ArticleEntity.DatasBean>) {
        mPresenter?.loadArticles(pageNum)
        articleAdapter.setList(list)
    }

    override fun showArticlesList(list: MutableList<ArticleEntity.DatasBean>) {
        swipeRefresh.isRefreshing = false
        articleAdapter.loadMoreModule.isEnableLoadMore = true
        articleAdapter.addData(list)
        if (list.size < Const.PAGE_SIZE) {
            //如果不够一页,显示没有更多数据布局
            articleAdapter.loadMoreModule.loadMoreEnd()
        } else {
            articleAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun showBanner(bannerList: MutableList<BannerEntity>) {
        banner.adapter = ImageNetAdapter(bannerList)
    }

    override fun collectSuccess() {

    }

    override fun unCollectSuccess() {

    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        swipeRefresh.isRefreshing = false
        articleAdapter.loadMoreModule.isEnableLoadMore = (true)
        articleAdapter.loadMoreModule.loadMoreFail()
    }

    override fun onStart() {
        super.onStart()
        banner.start()
    }

    override fun onStop() {
        super.onStop()
        banner.stop()
    }

}