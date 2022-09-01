package com.wan.android.ui.tab.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.wan.android.R
import com.wan.android.adapter.ArticleAdapter
import com.wan.android.constant.Const
import com.wan.android.databinding.FragmentArticleListBinding
import com.wan.android.ui.ArticleViewModel
import com.wan.android.ui.collect.CollectViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.baselib.widget.LoadMoreView
import com.wan.common.arouter.ArouterPath
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author cy
 * Create at 2020/4/2.
 */
@AndroidEntryPoint
class TabListFragment :
    BaseVMFragment<ArticleViewModel, FragmentArticleListBinding>() {

    private val articleViewModel by viewModels<ArticleViewModel>()
    private val collectViewModel by viewModels<CollectViewModel>()
    private val articleAdapter by lazy { ArticleAdapter() }
    private var projectId: Int = 0
    private var name: String? = null
    private var type: Int? = null
    private var curPosition = 0

    override fun attachLayoutRes(): Int = R.layout.fragment_article_list

    override fun initData(savedInstanceState: Bundle?) {
        type = arguments?.getInt(Const.TYPE)
        projectId = arguments?.getInt(Const.ID) ?: 0
        name = arguments?.getString(Const.NAME) ?: ""
    }

    override fun initView(view: View) {
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        binding.rvTabList.layoutManager = LinearLayoutManager(context)
        binding.rvTabList.adapter = articleAdapter
        articleAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        articleAdapter.loadMoreModule.loadMoreView = LoadMoreView()
        articleAdapter.loadMoreModule.isAutoLoadMore = true
        articleAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        articleAdapter.setOnItemClickListener(mOnItemClickListener)
        articleAdapter.addChildClickViewIds(R.id.ivCollect)
        articleAdapter.setOnItemChildClickListener(mOnItemChildClickListener)
    }

    private val mOnItemClickListener = OnItemClickListener { _, _, position ->
        val datasBean = articleAdapter.data[position]
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(com.wan.common.constant.Const.WEB_TITLE, datasBean.title)
            .withString(com.wan.common.constant.Const.WEB_URL, datasBean.link)
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
        type?.let {
            if (type == Const.PROJECT_TYPE)
                articleViewModel.getProjectList(true, projectId)
            else articleViewModel.getAccountList(true, projectId)
        }

    }

    private fun loadMore() {
        type?.let {
            if (type == Const.PROJECT_TYPE)
                articleViewModel.getProjectList(false, projectId)
            else articleViewModel.getAccountList(false, projectId)
        }
    }

    override fun subscribeUi() {
        articleViewModel.uiState.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it.showLoading
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
        }
        collectViewModel.uiState.observe(viewLifecycleOwner) {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { collect ->
                articleAdapter.data[curPosition].collect = collect
                articleAdapter.notifyItemChanged(curPosition)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        }
    }

}