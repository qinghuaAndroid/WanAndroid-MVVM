package com.qh.wanandroid.ui.tab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.devlibrary.ext.getThemeColor
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvp.BaseMvpFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ViewPagerAdapter
import com.qh.wanandroid.bean.TabEntity
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.databinding.FragmentTabBinding
import com.qh.wanandroid.ui.tab.list.TabListFragment
import org.jetbrains.anko.backgroundColor

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
        receiveNotice()
    }

    override fun initView(view: View) {
        super.initView(view)
        setThemeColor()
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
            mBinding.viewPager.adapter = ViewPagerAdapter(it, fragmentList)
            TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
                tab.text = titleList[position]
            }.attach()
        }
    }

    private fun receiveNotice() {
        LiveEventBusHelper.observe(com.example.common.constant.Const.THEME_COLOR,
            Int::class.java, this, Observer<Int> {
                setThemeColor()
            })
    }

    private fun setThemeColor() {
        context?.let { mBinding.tabLayout.backgroundColor = getThemeColor(it) }
    }
}