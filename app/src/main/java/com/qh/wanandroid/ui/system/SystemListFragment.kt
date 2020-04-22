package com.qh.wanandroid.ui.system

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMFragment
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.SystemListAdapter
import com.qh.wanandroid.const.Const
import com.qh.wanandroid.databinding.FragmentSystemListBinding
import com.qh.wanandroid.listener.OnLabelClickListener
import com.qh.wanandroid.ui.system.act.SystemActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListFragment : BaseVMFragment<SystemListViewModel, FragmentSystemListBinding>() {

    private val mViewModel by viewModel<SystemListViewModel>()

    private val systemListAdapter by lazy { SystemListAdapter(null) }

    override fun attachLayoutRes(): Int = R.layout.fragment_system_list

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.rvSystem.layoutManager = LinearLayoutManager(context)
        mBinding.rvSystem.adapter = systemListAdapter
        systemListAdapter.setOnLabelClickListener(object : OnLabelClickListener {
            override fun onLabelClick(helper: BaseViewHolder, i: Int, j: Int) {
                val childrenBean = systemListAdapter.data[i].children[j]
                val id = childrenBean.id
                val title = childrenBean.name
                activity?.let {
                    Intent(it, SystemActivity::class.java).run {
                        putExtra(Const.SYSTEM_ID, id)
                        putExtra(Const.SYSTEM_TITLE, title)
                        it.startActivity(this)
                    }
                }
            }
        })
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