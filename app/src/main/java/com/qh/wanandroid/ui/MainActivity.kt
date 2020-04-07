package com.qh.wanandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.devlibrary.base.BaseActivity
import com.qh.wanandroid.R
import com.qh.wanandroid.adapter.ViewPagerAdapter
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.databinding.ActivityMainBinding
import com.qh.wanandroid.ui.home.HomeFragment
import com.qh.wanandroid.ui.me.MineFragment
import com.qh.wanandroid.ui.system.SystemFragment
import com.qh.wanandroid.ui.tab.TabFragment
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val fragments = ArrayList<Fragment>()

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initData() {
        //首页
        fragments.add(HomeFragment())
        //项目
        val project = TabFragment()
        val proBundle = Bundle()
        proBundle.putInt(Const.TYPE, Const.PROJECT_TYPE)
        project.arguments = proBundle
        fragments.add(project)
        //体系
        fragments.add(SystemFragment())
        //公众号
        val account = TabFragment()
        val accountBundle = Bundle()
        accountBundle.putInt(Const.TYPE, Const.ACCOUNT_TYPE)
        account.arguments = accountBundle
        fragments.add(account)
        //我的
        fragments.add(MineFragment())
    }

    override fun initView() {
        initViewPager()
        initBottom()
    }

    private fun initViewPager() {
        mBinding.viewPager.isUserInputEnabled = false
        mBinding.viewPager.adapter = ViewPagerAdapter(this, fragments)
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.btmNavigation.selectedItemId = when (position) {
                    0 -> R.id.menu_home
                    1 -> R.id.menu_project
                    2 -> R.id.menu_square
                    3 -> R.id.menu_official_account
                    4 -> R.id.menu_mine
                    else -> 0
                }
            }
        })
    }

    override fun loadData() {

    }

    private fun initBottom() {
        mBinding.btmNavigation.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> mBinding.viewPager.currentItem = 0
                    R.id.menu_project -> mBinding.viewPager.currentItem = 1
                    R.id.menu_square -> mBinding.viewPager.currentItem = 2
                    R.id.menu_official_account -> mBinding.viewPager.currentItem = 3
                    R.id.menu_mine -> mBinding.viewPager.currentItem = 4
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
    }
}
