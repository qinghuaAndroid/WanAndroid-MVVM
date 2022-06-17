package com.wan.android.ui.system

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.android.R
import com.wan.android.adapter.SystemListAdapter
import com.wan.android.databinding.FragmentSystemListBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author cy
 * Create at 2020/4/8.
 */
@AndroidEntryPoint
class SystemListFragment : BaseVMFragment<SystemListViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModels<SystemListViewModel>()

    private val systemListAdapter by lazy { SystemListAdapter() }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        binding.rvSystem.layoutManager = LinearLayoutManager(context)
        binding.rvSystem.adapter = systemListAdapter
    }

    override fun loadData() {
        mViewModel.getSystemList()
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner,
            Observer { uiModel ->
                uiModel.showSuccess?.let { systemListAdapter.setList(it) }
                uiModel.showError?.let { showToast(it) }
            })
    }
}