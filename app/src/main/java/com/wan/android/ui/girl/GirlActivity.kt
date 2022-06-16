package com.wan.android.ui.girl

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.baselib.mvp.BaseMvpActivity
import com.wan.baselib.widget.LoadMoreView
import com.wan.android.R
import com.wan.android.adapter.GirlAdapter
import com.wan.common.arouter.ArouterPath
import com.wan.android.bean.GankIoDataBean
import com.wan.android.databinding.ActivityGirlBinding

/**
 * Description:
 * Created by cy on 2019/10/14.
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

    override fun getLayoutId(): Int = R.layout.activity_girl

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        binding.rvGirl.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvGirl.adapter = girlAdapter
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