package com.wan.android.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wan.android.bean.TabEntity
import com.wan.android.constant.Const
import com.wan.android.ui.tab.list.TabListFragment

/**
 * @author cy
 * Create at 2020/3/26.
 */
class TabPagerAdapter(fragment: Fragment, list: MutableList<TabEntity>, type: Int?) :
    FragmentStateAdapter(fragment) {

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