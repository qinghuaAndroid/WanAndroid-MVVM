package com.wan.android.ui.navigation

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.android.R
import com.wan.android.adapter.NavigationAdapter
import com.wan.android.databinding.FragmentSystemListBinding
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