package com.qh.wanandroid.ui.collect

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.common.constant.Const
import com.example.devlibrary.mvp.BaseMvpActivity
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.CollectAdapter
import com.qh.wanandroid.databinding.ActivityCollectBinding
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.zs.wanandroid.entity.CollectEntity

/**
 * @author FQH
 * Create at 2020/4/13.
 */
class CollectActivity :
    BaseMvpActivity<CollectContract.View, CollectContract.Presenter, ActivityCollectBinding>(),
    CollectContract.View {

    private val collectAdapter by lazy { CollectAdapter(R.layout.item_home_article) }
    private var isRefresh = false
    private var pageNum = 0
    private var curPosition = 0

    override fun createPresenter(): CollectContract.Presenter = CollectPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_collect

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setPageTitle("我的收藏")
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        mBinding.rvCollect.layoutManager = LinearLayoutManager(this)
        mBinding.rvCollect.adapter = collectAdapter
        collectAdapter.run {
            loadMoreModule.setOnLoadMoreListener { loadMore() }
            loadMoreModule.loadMoreView = LoadMoreView()
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.isEnableLoadMoreIfNotFullPage = true
            setOnItemClickListener(mOnItemClickListener)
            addChildClickViewIds(R.id.ivCollect)
            setOnItemChildClickListener(mOnItemChildClickListener)
        }
    }

    private val mOnItemClickListener = OnItemClickListener { _, _, position ->
        val datasBean = collectAdapter.data[position]
        Intent(this, BrowserNormalActivity::class.java).run {
            putExtra(Const.WEB_TITLE, datasBean.title)
            putExtra(Const.WEB_URL, datasBean.link)
            startActivity(this)
        }
    }

    private val mOnItemChildClickListener = OnItemChildClickListener { _, view, position ->
        val datasBean = collectAdapter.data[position]
        curPosition = position
        when (view.id) {
            R.id.ivCollect -> mPresenter?.cancelCollect(datasBean.id)
        }
    }

    override fun loadData() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        collectAdapter.loadMoreModule.isEnableLoadMore = false
        pageNum = 0 // 下拉刷新，需要重置页数
        isRefresh = true
        mPresenter?.loadData(pageNum)
    }

    private fun loadMore() {
        ++pageNum
        isRefresh = false
        mPresenter?.loadData(pageNum)
    }

    override fun showList(collectEntity: CollectEntity) {
        mBinding.swipeRefresh.isRefreshing = false
        collectAdapter.loadMoreModule.isEnableLoadMore = true
        collectEntity.datas?.let {
            if (isRefresh) {
                collectAdapter.setList(it)
            } else {
                collectAdapter.addData(it)
            }
        }
        if (collectEntity.curPage >= collectEntity.pageCount) {
            //如果不够一页,显示没有更多数据布局
            collectAdapter.loadMoreModule.loadMoreEnd()
        } else {
            collectAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun loadDataFail() {
        mBinding.swipeRefresh.isRefreshing = false
        collectAdapter.loadMoreModule.isEnableLoadMore = (true)
        collectAdapter.loadMoreModule.loadMoreFail()
    }

    override fun cancelCollectSuccess() {
        collectAdapter.remove(curPosition)
    }
}