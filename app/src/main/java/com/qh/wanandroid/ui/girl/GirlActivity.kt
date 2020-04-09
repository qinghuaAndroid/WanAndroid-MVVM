package com.qh.wanandroid.ui.girl

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.devlibrary.mvp.BaseMvpActivity
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.GirlAdapter
import com.qh.wanandroid.bean.GankIoDataBean
import com.qh.wanandroid.databinding.ActivityGirlBinding

/**
 * Description:
 * Created by FQH on 2019/10/14.
 */
class GirlActivity :
    BaseMvpActivity<GirlContract.View, GirlContract.Presenter, ActivityGirlBinding>(),
    GirlContract.View {

    private val girlAdapter by lazy { GirlAdapter() }
    private val limit = 20
    private var page = 0

    override fun createPresenter(): GirlContract.Presenter = GirlPresenter()

    override fun setMeiziList(resultBeanList: List<GankIoDataBean.ResultBean>) {
        if (page == 1) {
            girlAdapter.setList(resultBeanList)
        } else {
            girlAdapter.addData(resultBeanList)
        }
        if (resultBeanList.size < limit) {
            girlAdapter.loadMoreModule.loadMoreEnd(false)
        } else {
            girlAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun loadMeiziListFail(errorMsg: String) {
        --page
        if (page > 0) {
            girlAdapter.loadMoreModule.loadMoreFail()
        }
    }

    override fun attachLayoutRes(): Int = R.layout.activity_girl

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        mBinding.rvGirl.layoutManager = LinearLayoutManager(this)
        mBinding.rvGirl.adapter = girlAdapter
        PagerSnapHelper().attachToRecyclerView(mBinding.rvGirl)
        girlAdapter.loadMoreModule.loadMoreView = LoadMoreView()
        girlAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
    }

    override fun loadData() {
        mPresenter?.requestMeiziList("福利", limit, ++page)
    }

    fun loadMore() {
        mPresenter?.requestMeiziList("福利", limit, ++page)
    }
}