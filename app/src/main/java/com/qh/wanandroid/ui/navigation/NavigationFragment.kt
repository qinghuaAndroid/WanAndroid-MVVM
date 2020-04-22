package com.qh.wanandroid.ui.navigation

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.constant.Const
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMFragment
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.NavigationAdapter
import com.qh.wanandroid.arouter.ArouterPath
import com.qh.wanandroid.databinding.FragmentSystemListBinding
import com.qh.wanandroid.listener.OnLabelClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class NavigationFragment : BaseVMFragment<NavigationViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModel<NavigationViewModel>()

    private val navigationAdapter by lazy { NavigationAdapter(null) }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.rvSystem.layoutManager = LinearLayoutManager(context)
        mBinding.rvSystem.adapter = navigationAdapter
        navigationAdapter.setOnLabelClickListener(object : OnLabelClickListener {
            override fun onLabelClick(helper: BaseViewHolder, i: Int, j: Int) {
                val datasBean = navigationAdapter.data[i].articles[j]
                ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
                    .withString(Const.WEB_TITLE, datasBean.title)
                    .withString(Const.WEB_URL, datasBean.link)
                    .navigation()
            }
        })
    }

    override fun loadData() {
        mViewModel.getNavigation()
    }

    override fun startObserve() {
        mViewModel.uiState.observe(this,
            Observer { uiModel ->
                uiModel.showSuccess?.let { navigationAdapter.setList(it) }
                uiModel.showError?.let { showToast(it) }
            })
    }
}