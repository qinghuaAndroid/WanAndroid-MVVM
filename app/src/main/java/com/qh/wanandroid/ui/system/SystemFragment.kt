package com.qh.wanandroid.ui.system

import android.view.View
import androidx.fragment.app.Fragment
import com.example.devlibrary.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ViewPagerAdapter
import com.qh.wanandroid.databinding.FragmentSystemBinding
import com.qh.wanandroid.ui.system.list.SystemListFragment
import com.qh.wanandroid.ui.system.navigation.NavigationFragment

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    override fun attachLayoutRes(): Int = R.layout.fragment_system

    override fun initData() {

    }

    override fun initView(view: View) {
        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(SystemListFragment())
        fragmentList.add(NavigationFragment())
        val titleList = mutableListOf<String>()
        titleList.add(getString(R.string.system))
        titleList.add(getString(R.string.navigation))
        activity?.let {
            mBinding.viewPager.adapter = ViewPagerAdapter(it, fragmentList)
            TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
                tab.text = titleList[position]
            }.attach()
        }
    }

    override fun loadData() {

    }
}