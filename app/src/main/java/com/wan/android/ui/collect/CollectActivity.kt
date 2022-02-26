package com.wan.android.ui.collect

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.wan.common.constant.Const
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.widget.LoadMoreView
import com.wan.android.R
import com.wan.android.adapter.CollectAdapter
import com.wan.common.arouter.ArouterPath
import com.wan.android.databinding.ActivityCollectBinding
import com.wan.android.ui.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author cy
 * Create at 2020/4/13.
 */
@Route(path = ArouterPath.ACTIVITY_COLLECT, extras = Const.NEED_LOGIN)
class CollectActivity :
    BaseVMActivity<ArticleViewModel, ActivityCollectBinding>() {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val collectViewModel by viewModel<CollectViewModel>()
    private val collectAdapter by lazy { CollectAdapter() }
    private var curPosition = 0

    override fun getLayoutId(): Int = R.layout.activity_collect

    override fun initData() {

    }

    override fun initView() {
        title = "我的收藏"
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
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(Const.WEB_TITLE, datasBean.title)
            .withString(Const.WEB_URL, datasBean.link)
            .navigation()
    }

    private val mOnItemChildClickListener = OnItemChildClickListener { _, view, position ->
        val datasBean = collectAdapter.data[position]
        curPosition = position
        when (view.id) {
            R.id.ivCollect -> {
                collectViewModel.unMyCollect(datasBean.id, datasBean.originId)
            }
        }
    }

    override fun loadData() {
        articleViewModel.getCollectData(true)
    }

    private fun loadMore() {
        articleViewModel.getCollectData(false)
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(this, Observer {
            mBinding.swipeRefresh.isRefreshing = it.showLoading
            it.showSuccess?.let { articleEntity ->
                articleEntity.datas?.let { list ->
                    if (it.isRefresh) collectAdapter.setList(list)
                    else collectAdapter.addData(list)
                }
                collectAdapter.loadMoreModule.loadMoreComplete()
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
                collectAdapter.loadMoreModule.loadMoreFail()
            }
            if (it.showEnd) collectAdapter.loadMoreModule.loadMoreEnd()
            collectAdapter.loadMoreModule.isEnableLoadMore = it.isEnableLoadMore
        })
        collectViewModel.uiState.observe(this, Observer {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { collect ->
                if (collect.not()) {
                    collectAdapter.remove(curPosition)
                }
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        })
    }
}