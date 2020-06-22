package com.qh.wanandroid.ui.integral

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.constant.Const
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMActivity
import com.example.devlibrary.widget.LoadMoreView
import com.google.android.material.appbar.AppBarLayout
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.IntegralAdapter
import com.example.common.arouter.ArouterPath
import com.qh.wanandroid.bean.UserInfoEntity
import com.qh.wanandroid.databinding.ActivityIntegralBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/10.
 */
@Route(path = ArouterPath.ACTIVITY_INTEGRAL, extras = Const.NEED_LOGIN)
class IntegralActivity : BaseVMActivity<IntegralViewModel, ActivityIntegralBinding>() {

    @Autowired(name = com.qh.wanandroid.const.Const.USER_INFO_ENTITY)
    @JvmField
    var userInfoEntity: UserInfoEntity? = null
    private val integralViewModel by viewModel<IntegralViewModel>()
    private val integralAdapter by lazy { IntegralAdapter(R.layout.item_integral) }
    private lateinit var headerView: View

    override fun startObserve() {
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
        ARouter.getInstance().inject(this)
    }

    override fun initView() {
        title = getString(R.string.my_integral)
        startAnim()
        initRecyclerView()
        mBinding.swipeRefresh.setOnRefreshListener { loadData() }
        mBinding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                mBinding.swipeRefresh.isEnabled = verticalOffset >= 0
            })
    }

    /**
     * 开启积分动画
     */
    private fun startAnim(){
        userInfoEntity?.apply {
            val animator = ValueAnimator.ofInt(0,coinCount)
            //播放时长
            animator.duration = 1500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                //获取改变后的值
                val currentValue = animation.animatedValue as Int
                mBinding.tvIntegralAnim.text = "$currentValue"
            }
            animator.start()
        }
    }

    private fun initRecyclerView() {
        mBinding.rvIntegralList.run {
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