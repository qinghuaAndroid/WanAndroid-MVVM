package com.qh.wanandroid.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qh.wanandroid.bean.TabEntity
import com.qh.wanandroid.const.Const
import com.qh.wanandroid.ui.home.HomeFragment
import com.qh.wanandroid.ui.navigation.NavigationFragment
import com.qh.wanandroid.ui.system.SystemListFragment
import com.qh.wanandroid.ui.tab.TabFragment
import com.qh.wanandroid.ui.tab.list.TabListFragment

/**
 * @author FQH
 * Create at 2020/3/26.
 */
class TabPagerAdapter(fragmentActivity: FragmentActivity, list: MutableList<TabEntity>,type: Int?) :
    FragmentStateAdapter(fragmentActivity) {

    private val list by lazy { list }
    private val type by lazy { type }

    override fun createFragment(position: Int): Fragment {
        return getFragment(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getFragment(position: Int): Fragment {
        return TabListFragment().apply {
            list[position].run {
                val bundle = Bundle()
                type?.let { it1 -> bundle.putInt(Const.TYPE, it1) }
                bundle.putInt(Const.ID, id)
                bundle.putString(Const.NAME, name)
                arguments = bundle
            }
        }
    }
}