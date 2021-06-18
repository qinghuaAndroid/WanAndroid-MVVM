package com.qh.wanandroid.ui.system

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.SystemListAdapter
import com.qh.wanandroid.databinding.FragmentSystemListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListFragment : BaseVMFragment<SystemListViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModel<SystemListViewModel>()

    private val systemListAdapter by lazy { SystemListAdapter() }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.rvSystem.layoutManager = LinearLayoutManager(context)
        mBinding.rvSystem.adapter = systemListAdapter
    }

    override fun loadData() {
        mViewModel.getSystemList()
    }

    override fun startObserve() {
        mViewModel.uiState.observe(this,
            Observer { uiModel ->
                uiModel.showSuccess?.let { systemListAdapter.setList(it) }
                uiModel.showError?.let { showToast(it) }
            })
    }
}