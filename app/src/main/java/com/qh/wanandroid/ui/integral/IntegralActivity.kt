package com.qh.wanandroid.ui.integral

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMActivity
import com.example.devlibrary.widget.LoadMoreView
import com.google.android.material.appbar.AppBarLayout
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.IntegralAdapter
import com.qh.wanandroid.databinding.ActivityIntegralBinding
import com.qh.wanandroid.ui.me.MineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class IntegralActivity : BaseVMActivity<IntegralViewModel, ActivityIntegralBinding>() {

    private val mineViewModel by viewModel<MineViewModel>()
    private val integralViewModel by viewModel<IntegralViewModel>()
    private val integralAdapter by lazy { IntegralAdapter(R.layout.item_integral) }

    override fun startObserve() {
        mineViewModel.integralData.observe(this, Observer {
            val animator = ValueAnimator.ofInt(0, it.coinCount)
            //播放时长
            animator.duration = 1500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                //获取改变后的值
                val currentValue = animation.animatedValue as Int
                mBinding.tvIntegralAnim.text = "$currentValue"
            }
            animator.start()
        })
        integralViewModel.uiState.observe(this, Observer {
            mBinding.swipeRefresh.isRefreshing = it.showLoading
            it.showSuccess?.let { articleEntity ->
                articleEntity.datas?.let { list ->
                    if (it.isRefresh) integralAdapter.setList(list)
                    else integralAdapter.addData(list)
                }
                integralAdapter.loadMoreModule.loadMoreComplete()
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
                integralAdapter.loadMoreModule.loadMoreFail()
            }
            if (it.showEnd) integralAdapter.loadMoreModule.loadMoreEnd()
            integralAdapter.loadMoreModule.isEnableLoadMore = it.isEnableLoadMore
        })
    }

    override fun attachLayoutRes(): Int = R.layout.activity_integral

    override fun initData() {

    }

    override fun initView() {
        setPageTitle(getString(R.string.my_integral))
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
        mBinding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                mBinding.swipeRefresh.isEnabled = verticalOffset >= 0
            })
    }

    private fun initRecyclerView() {
        mBinding.rvIntegralList.layoutManager = LinearLayoutManager(this)
        mBinding.rvIntegralList.adapter = integralAdapter
        integralAdapter.loadMoreModule.loadMoreView = LoadMoreView()
        integralAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        integralAdapter.loadMoreModule.isAutoLoadMore = true
        integralAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }

    override fun loadData() {
        mineViewModel.getIntegral()
        integralViewModel.getIntegralRecord(true)
    }

    private fun loadMore() {
        integralViewModel.getIntegralRecord(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.tvIntegralAnim.clearAnimation()
    }
}