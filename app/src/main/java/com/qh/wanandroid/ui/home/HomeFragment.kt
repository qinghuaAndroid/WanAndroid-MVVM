package com.qh.wanandroid.ui.home

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.common.constant.Const
import com.example.devlibrary.ext.getThemeColor
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvp.BaseMvpFragment
import com.example.devlibrary.network.exception.ErrorStatus
import com.example.devlibrary.utils.DisplayUtils
import com.example.devlibrary.utils.StatusBarUtil
import com.example.devlibrary.widget.LoadMoreView
import com.google.android.material.appbar.AppBarLayout
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.adapter.ImageNetAdapter
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity
import com.qh.wanandroid.databinding.FragmentHomeBinding
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.qh.wanandroid.ui.login.LoginActivity
import com.qh.wanandroid.ui.search.SearchActivity
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class HomeFragment :
    BaseMvpFragment<HomeContract.View, HomeContract.Presenter, FragmentHomeBinding>(),
    HomeContract.View {

    private var criticalValue: Int = 0
    private val articleAdapter by lazy { ArticleAdapter() }
    private var isRefresh = false
    private var pageNum = 0
    private var curPosition = 0

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initData() {
        val statusBarHeight = StatusBarUtil.getStatusBarHeight(context)
        criticalValue = DisplayUtils.dp2px(215F) - statusBarHeight
        receiveNotice()
    }

    override fun initView(view: View) {
        super.initView(view)
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
                if (datasBean.collect) mPresenter?.unCollect(datasBean.id)
                else mPresenter?.collect(datasBean.id)
            }
        }
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

    override fun showArticlesList(articleEntity: ArticleEntity) {
        mBinding.swipeRefresh.isRefreshing = false
        articleAdapter.loadMoreModule.isEnableLoadMore = true
        articleEntity.datas?.let { articleAdapter.addData(it) }
        if (articleEntity.curPage >= articleEntity.pageCount) {
            //如果不够一页,显示没有更多数据布局
            articleAdapter.loadMoreModule.loadMoreEnd()
        } else {
            articleAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun showBanner(bannerList: MutableList<BannerEntity>) {
        mBinding.banner.adapter = ImageNetAdapter(bannerList)
    }

    override fun collectSuccess() {
        articleAdapter.data[curPosition].collect = true
        articleAdapter.notifyItemChanged(curPosition)
    }

    override fun unCollectSuccess() {
        articleAdapter.data[curPosition].collect = false
        articleAdapter.notifyItemChanged(curPosition)
    }

    override fun loadArticlesFail() {
        mBinding.swipeRefresh.isRefreshing = false
        articleAdapter.loadMoreModule.isEnableLoadMore = (true)
        articleAdapter.loadMoreModule.loadMoreFail()
    }

    override fun showError(errorCode: Int, errorMsg: String?) {
        super.showError(errorCode, errorMsg)
        if (errorCode == ErrorStatus.LOGOUT_ERROR) {
            startActivity<LoginActivity>()
        }
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
}