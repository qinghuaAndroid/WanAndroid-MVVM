package com.wan.android.ui.navigation

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.android.R
import com.wan.android.adapter.NavigationAdapter
import com.wan.android.databinding.FragmentSystemListBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author cy
 * Create at 2020/4/8.
 */
@AndroidEntryPoint
class NavigationFragment : BaseVMFragment<NavigationViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModels<NavigationViewModel>()

    private val navigationAdapter by lazy { NavigationAdapter() }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        binding.rvSystem.layoutManager = LinearLayoutManager(context)
        binding.rvSystem.adapter = navigationAdapter
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