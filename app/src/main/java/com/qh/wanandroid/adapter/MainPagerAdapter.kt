package com.qh.wanandroid.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.ui.home.HomeFragment
import com.qh.wanandroid.ui.navigation.NavigationFragment
import com.qh.wanandroid.ui.system.SystemListFragment
import com.qh.wanandroid.ui.tab.TabFragment

/**
 * @author FQH
 * Create at 2020/3/26.
 */
class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return getFragment(position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment() //首页
            1 -> SystemListFragment() //体系
            2 -> TabFragment().apply { //公众号
                val accountBundle = Bundle()
                accountBundle.putInt(Const.TYPE, Const.ACCOUNT_TYPE)
                arguments = accountBundle
            }
            3 -> NavigationFragment() //导航
            4 -> TabFragment().apply { //项目
                val proBundle = Bundle()
                proBundle.putInt(Const.TYPE, Const.PROJECT_TYPE)
                arguments = proBundle
            }
            else -> Fragment()
        }
    }
}