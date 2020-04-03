package com.qh.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author FQH
 * Create at 2020/3/26.
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity, fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList by lazy { fragmentList }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}