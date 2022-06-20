package com.wan.android.ui.integral

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import com.wan.android.R
import com.wan.android.adapter.IntegralAdapter
import com.wan.android.bean.CoinInfo
import com.wan.android.databinding.ActivityIntegralBinding
import com.wan.android.ui.main.MainViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.widget.LoadMoreView
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author cy
 * Create at 2020/4/10.
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_INTEGRAL, extras = Const.NEED_LOGIN)
class IntegralActivity : BaseVMActivity<IntegralViewModel, ActivityIntegralBinding>() {

    private val integralViewModel by viewModels<IntegralViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private val integralAdapter by lazy { IntegralAdapter() }
    private lateinit var headerView: View

    override fun startObserve() {
        mainViewModel.uiState.observe(this) {
            it.showSuccess?.let { userInfoEntity ->
                userInfoEntity.coinInfo?.let { coinInfo ->
                    startAnim(coinInfo)
                }
            }
        }

        integralViewModel.uiState.observe(this) {
            binding.swipeRefresh.isRefreshing = it.showLoading
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
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_integral

    override fun initData() {
        ARouter.getInstance().inject(this)
    }

    override fun initView() {
        title = getString(R.string.my_integral)
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener { loadData() }
        binding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                binding.swipeRefresh.isEnabled = verticalOffset >= 0
            })
    }

    /**
     * 开启积分动画
     */
    private fun startAnim(coinInfo: CoinInfo) {
        val animator = ValueAnimator.ofInt(0, coinInfo.coinCount)
        //播放时长
        animator.duration = 1500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            //获取改变后的值
            val currentValue = animation.animatedValue as Int
            binding.tvIntegralAnim.text = "$currentValue"
        }
        animator.start()
    }

    private fun initRecyclerView() {
        binding.rvIntegralList.run {
            layoutManager = LinearLayoutManager(this@IntegralActivity)
            adapter = integralAdapter
            headerView = layoutInflater.inflate(R.layout.header_integral, this, false)
        }
        integralAdapter.run {
            addHeaderView(headerView)
            loadMoreModule.loadMoreView = LoadMoreView()
            loadMoreModule.setOnLoadMoreListener { loadMore() }
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        }
    }

    override fun loadData() {
        mainViewModel.getUserInfo()
        integralViewModel.getIntegralRecord(true)
    }

    private fun loadMore() {
        integralViewModel.getIntegralRecord(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.tvIntegralAnim.clearAnimation()
    }
}