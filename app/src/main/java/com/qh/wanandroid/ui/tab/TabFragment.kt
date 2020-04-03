package com.qh.wanandroid.ui.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.devlibrary.mvp.BaseMvpFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ViewPagerAdapter
import com.qh.wanandroid.bean.TabEntity
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.databinding.FragmentTabBinding
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class TabFragment : BaseMvpFragment<TabContract.View, TabContract.Presenter, FragmentTabBinding>(),
    TabContract.View {

    private var type: Int? = null

    override fun attachLayoutRes(): Int = R.layout.fragment_tab

    override fun initData() {
        type = arguments?.getInt(Const.TYPE)
    }

    override fun loadData() {
        type?.let {
            mPresenter?.loadData(it)
        }
    }

    override fun createPresenter(): TabContract.Presenter = TabPresenter()

    override fun showList(list: MutableList<TabEntity>) {
        val fragmentList = mutableListOf<Fragment>()
        val titleList = mutableListOf<String>()
        list.forEach {
            val fragment = TabListFragment()
            val bundle = Bundle()
            type?.let { it1 -> bundle.putInt(Const.TYPE, it1) }
            bundle.putInt(Const.ID, it.id)
            bundle.putString(Const.NAME, it.name)
            fragment.arguments = bundle
            fragmentList.add(fragment)
            it.name?.let { it1 -> titleList.add(it1) }
        }
        activity?.let {
            viewPager.adapter = ViewPagerAdapter(it, fragmentList)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = titleList[position]
            }.attach()
        }
    }
}