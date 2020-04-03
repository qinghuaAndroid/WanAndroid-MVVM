package com.qh.wanandroid.ui.tab

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devlibrary.mvp.BaseMvpFragment
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ArticleAdapter
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.databinding.FragmentArticleListBinding
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class TabListFragment :
    BaseMvpFragment<TabListContract.View, TabListContract.Presenter, FragmentArticleListBinding>(),
    TabListContract.View {

    private val articleList: MutableList<ArticleEntity.DatasBean> = mutableListOf()
    private val articleAdapter by lazy { ArticleAdapter(articleList) }
    private var isRefresh = false
    private var pageNum = 0
    private var projectId: Int = 0
    private var name: String? = null
    private var type: Int? = null

    override fun attachLayoutRes(): Int = R.layout.fragment_article_list

    override fun initData() {
        type = arguments?.getInt(Const.TYPE)
        projectId = arguments?.getInt(Const.ID) ?: 0
        name = arguments?.getString(Const.NAME) ?: ""
    }

    override fun initView(view: View) {
        super.initView(view)
        initRecyclerView()
        swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        rvTabList.layoutManager = LinearLayoutManager(context)
        rvTabList.adapter = articleAdapter
        articleAdapter.setOnLoadMoreListener({ loadMore() }, rvTabList)
        articleAdapter.setLoadMoreView(LoadMoreView())
    }

    override fun loadData() {
        pageNum = 0
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        articleAdapter.setEnableLoadMore(false)
        articleList.clear()
        articleAdapter.setNewData(articleList)
        isRefresh = true
        type?.let { mPresenter?.loadData(it, projectId, pageNum) }
    }

    private fun loadMore() {
        ++pageNum
        isRefresh = false
        type?.let { mPresenter?.loadData(it, projectId, pageNum) }
    }

    override fun createPresenter(): TabListContract.Presenter {
        return TabListPresenter()
    }

    override fun showList(list: MutableList<ArticleEntity.DatasBean>) {
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
            articleAdapter.setEnableLoadMore(true)
        }
        articleAdapter.addData(list)
        articleAdapter.loadMoreComplete()
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
            articleAdapter.setEnableLoadMore(true)
        } else {
            articleAdapter.loadMoreFail()
        }
    }

    override fun collectSuccess() {

    }

    override fun unCollectSuccess() {

    }
}