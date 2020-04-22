package com.qh.wanandroid.ui.girl

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.devlibrary.mvp.BaseMvpActivity
import com.example.devlibrary.widget.LoadMoreView
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.GirlAdapter
import com.qh.wanandroid.arouter.ArouterPath
import com.qh.wanandroid.bean.GankIoDataBean
import com.qh.wanandroid.databinding.ActivityGirlBinding

/**
 * Description:
 * Created by FQH on 2019/10/14.
 */
@Route(path = ArouterPath.ACTIVITY_GIRL)
class GirlActivity :
    BaseMvpActivity<GirlContract.View, GirlContract.Presenter, ActivityGirlBinding>(),
    GirlContract.View {

    private val girlAdapter by lazy { GirlAdapter() }
    private val count = 10
    private var page = 0

    override fun createPresenter(): GirlContract.Presenter = GirlPresenter()

    override fun setMeiziList(resultBeanList: List<GankIoDataBean.ResultBean>) {
        girlAdapter.addData(resultBeanList)
        if (resultBeanList.size < count) {
            girlAdapter.loadMoreModule.loadMoreEnd(false)
        } else {
            girlAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun loadMeiziListFail(errorMsg: String) {
        girlAdapter.loadMoreModule.loadMoreFail()
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
        mPresenter?.requestMeiziList("Girl","Girl", count, ++page)
    }

    fun loadMore() {
        mPresenter?.requestMeiziList("Girl","Girl", count, ++page)
    }
}