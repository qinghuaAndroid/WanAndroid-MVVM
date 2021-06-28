package com.wan.android.ui.tab

import android.view.View
import androidx.lifecycle.Observer
import com.wan.baselib.ext.getThemeColor
import com.wan.baselib.helper.LiveEventBusHelper
import com.wan.baselib.mvp.BaseMvpFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.wan.android.R
import com.wan.android.adapter.TabPagerAdapter
import com.wan.android.bean.TabEntity
import com.wan.android.constant.Const
import com.wan.android.databinding.FragmentTabBinding
import org.jetbrains.anko.backgroundColor

/**
 * @author cy
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
        val titleList = mutableListOf<String>()
        list.forEach {
            it.name?.let { it1 -> titleList.add(it1) }
        }
        activity?.let {
            mBinding.viewPager.adapter = TabPagerAdapter(it, list, type)
            TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
                tab.text = titleList[position]
            }.attach()
        }
    }

    private fun receiveNotice() {
        LiveEventBusHelper.observe(com.wan.common.constant.Const.THEME_COLOR,
            Int::class.java, this, Observer<Int> {
                setThemeColor()
            })
    }

    private fun setThemeColor() {
        context?.let { mBinding.tabLayout.backgroundColor = getThemeColor(it) }
    }
}