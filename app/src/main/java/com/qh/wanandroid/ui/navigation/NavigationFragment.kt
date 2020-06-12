package com.qh.wanandroid.ui.navigation

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMFragment
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.NavigationAdapter
import com.qh.wanandroid.databinding.FragmentSystemListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class NavigationFragment : BaseVMFragment<NavigationViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModel<NavigationViewModel>()

    private val navigationAdapter by lazy { NavigationAdapter() }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.rvSystem.layoutManager = LinearLayoutManager(context)
        mBinding.rvSystem.adapter = navigationAdapter
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