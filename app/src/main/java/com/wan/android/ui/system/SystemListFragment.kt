package com.wan.android.ui.system

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.wan.android.R
import com.wan.android.adapter.SystemListAdapter
import com.wan.android.databinding.FragmentSystemListBinding
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.common.callback.EmptyCallback
import com.wan.common.callback.ErrorCallback
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author cy
 * Create at 2020/4/8.
 */
@AndroidEntryPoint
class SystemListFragment : BaseVMFragment<SystemListViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModels<SystemListViewModel>()

    private val systemListAdapter by lazy { SystemListAdapter() }

    private lateinit var loadService: LoadService<Any>

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(view: View) {
        //第二步：注册布局View
        loadService = LoadSir.getDefault().register(binding.rvSystem) { loadData() }
        binding.rvSystem.layoutManager = LinearLayoutManager(context)
        binding.rvSystem.adapter = systemListAdapter
    }

    override fun loadData() {
        mViewModel.getSystemList()
    }

    override fun subscribeUi() {
        mViewModel.uiState.observe(viewLifecycleOwner) { baseUiState ->
            baseUiState.showSuccess?.let {
                if (it.isEmpty()) {
                    loadService.showCallback(EmptyCallback::class.java)
                } else {
                    loadService.showSuccess()
                    systemListAdapter.setList(it)
                }
            }
            baseUiState.showError?.let {
                loadService.showCallback(ErrorCallback::class.java)
                showToast(it)
            }
        }
    }
}