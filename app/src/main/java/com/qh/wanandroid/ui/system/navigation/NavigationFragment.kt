package com.qh.wanandroid.ui.system.navigation

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.devlibrary.ext.showToast
import com.example.devlibrary.mvvm.BaseVMFragment
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.NavigationAdapter
import com.qh.wanandroid.databinding.FragmentSystemListBinding
import com.qh.wanandroid.listener.OnLabelClickListener
import com.qh.wanandroid.ui.BrowserNormalActivity
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
                activity?.let {
                    Intent(it, BrowserNormalActivity::class.java).run {
                        putExtra(com.example.common.constant.Const.WEB_TITLE, datasBean.title)
                        putExtra(com.example.common.constant.Const.WEB_URL, datasBean.link)
                        it.startActivity(this)
                    }
                }
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