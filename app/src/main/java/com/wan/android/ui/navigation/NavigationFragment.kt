package com.wan.android.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wan.android.R
import com.wan.android.adapter.NavigationAdapter
import com.wan.android.databinding.FragmentSystemListBinding
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
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

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(view: View) {
        binding.rvSystem.layoutManager = LinearLayoutManager(context)
        binding.rvSystem.adapter = navigationAdapter
    }

    override fun loadData() {
        mViewModel.getNavigation()
    }

    override fun subscribeUi() {
        mViewModel.uiState.observe(viewLifecycleOwner) { baseUiState ->
            baseUiState.showSuccess?.let { navigationAdapter.setList(it) }
            baseUiState.showError?.let { showToast(it) }
        }
    }
}