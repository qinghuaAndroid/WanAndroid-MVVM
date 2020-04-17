package com.qh.wanandroid.ui.system

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.devlibrary.base.BaseFragment
import com.example.devlibrary.ext.getThemeColor
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.utils.StatusBarUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ViewPagerAdapter
import com.qh.wanandroid.databinding.FragmentSystemBinding
import com.qh.wanandroid.ui.system.list.SystemListFragment
import com.qh.wanandroid.ui.system.navigation.NavigationFragment
import org.jetbrains.anko.backgroundColor

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    override fun attachLayoutRes(): Int = R.layout.fragment_system

    override fun initData() {
        receiveNotice()
    }

    override fun initView(view: View) {
        setThemeColor()
        StatusBarUtil.setPaddingTop(context, mBinding.tabLayout)
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

    private fun receiveNotice() {
        LiveEventBusHelper.observe(com.example.common.constant.Const.THEME_COLOR,
            Int::class.java, this, Observer<Int> {
                setThemeColor()
            })
    }

    private fun setThemeColor() {
        mBinding.tabLayout.backgroundColor = getThemeColor(resources)
    }
}