package com.wan.android.ui.main

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.navigation.NavigationView
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wan.android.R
import com.wan.android.adapter.MainPagerAdapter
import com.wan.android.bean.CoinInfo
import com.wan.android.databinding.ActivityMainBinding
import com.wan.android.ui.account.AccountViewModel
import com.wan.baselib.ext.getThemeColor
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.utils.SettingUtil
import com.wan.common.arouter.ArouterPath
import com.wan.common.ext.navigation
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick


@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_MAIN)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {

    private val accountViewModel by viewModels<AccountViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var navHeaderView: View
    private lateinit var tvUserId: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserGrade: TextView
    private lateinit var tvUserRank: TextView
    private lateinit var logoutMenuItem: MenuItem

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        receiveNotice()
    }

    override fun initView() {
        initDrawerLayout()
        initNavView()
        setThemeColor()
        initViewPager()
        initBottom()
    }

    private fun initDrawerLayout() {
        binding.drawerLayout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                findViewById(com.wan.baselib.R.id.toolbar), R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun initViewPager() {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = MainPagerAdapter(this)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.btmNavigation.selectedItemId = when (position) {
                    0 -> R.id.menu_home
                    1 -> R.id.menu_system
                    2 -> R.id.menu_official_account
                    3 -> R.id.menu_navigation
                    4 -> R.id.menu_project
                    else -> 0
                }
                setTitle(
                    when (position) {
                        0 -> R.string.tab_1
                        1 -> R.string.tab_2
                        2 -> R.string.tab_3
                        3 -> R.string.tab_4
                        4 -> R.string.tab_5
                        else -> 0
                    }
                )
            }
        })
    }

    override fun loadData() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                ARouter.getInstance().build(ArouterPath.ACTIVITY_SEARCH).navigation()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * init NavigationView
     */
    private fun initNavView() {
        binding.navView.run {
            getHeaderView(0).run {
                navHeaderView = this
                tvUserId = findViewById(R.id.tv_user_id)
                tvUserName = findViewById(R.id.tv_username)
                tvUserGrade = findViewById(R.id.tv_user_grade)
                tvUserRank = findViewById(R.id.tv_user_rank)
            }
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            logoutMenuItem = menu.findItem(R.id.nav_logout)
        }
        tvUserName.onClick {
            if (accountViewModel.isLogin.not()) {
                ARouter.getInstance().build(ArouterPath.ACTIVITY_LOGIN).navigation()
            }
        }
    }

    /**
     * NavigationView 监听
     */
    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_score -> {
                    ARouter.getInstance().build(ArouterPath.ACTIVITY_INTEGRAL).navigation(this) {
                        onInterrupt {
                            ARouter.getInstance().build(ArouterPath.ACTIVITY_LOGIN).with(it?.extras)
                                .navigation()
                        }
                    }
                }
                R.id.nav_collect -> {
                    ARouter.getInstance().build(ArouterPath.ACTIVITY_COLLECT).navigation(this) {
                        onInterrupt {
                            ARouter.getInstance().build(ArouterPath.ACTIVITY_LOGIN).with(it?.extras)
                                .navigation()
                        }
                    }
                }
                R.id.nav_question -> {
                    ARouter.getInstance().build(ArouterPath.ACTIVITY_QUESTION).navigation()
                }
                R.id.nav_setting -> {
                    ARouter.getInstance().build(ArouterPath.ACTIVITY_SETTING).navigation()
                }
                R.id.nav_about_us -> {

                }
                R.id.nav_logout -> {
                    logout()
                }
                R.id.nav_night_mode -> {
                    switchNightMode()
                }
                R.id.nav_todo -> {

                }
                R.id.nav_square -> {
                    ARouter.getInstance().build(ArouterPath.ACTIVITY_SHARE_LIST).navigation()
                }
            }
            // drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

    private fun logout() {
        accountViewModel.logout()
    }

    /**
     * 切换日夜间模式
     */
    private fun switchNightMode() {
        if (SettingUtil.getIsNightMode()) {
            SettingUtil.setIsNightMode(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            SettingUtil.setIsNightMode(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
        recreate()
    }

    private fun initBottom() {
        binding.btmNavigation.run {
            //用于分配，检索，检查和清除徽章内显示的数字。默认情况下，显示的徽章没有数字
            val badge = getOrCreateBadge(R.id.menu_home)
            badge.clearNumber()

            val badge1 = getOrCreateBadge(R.id.menu_system)
            badge1.number = 2

            val badge2 = getOrCreateBadge(R.id.menu_official_account)
            badge2.number = 100
            //用于设置/获取徽章数字中允许的最大字符数，然后将其用'+'截断。预设值为4。
            badge2.maxCharacterCount = 3

            val badge3 = getOrCreateBadge(R.id.menu_navigation)
            badge3.clearNumber()

            val badge4 = getOrCreateBadge(R.id.menu_project)
            badge4.number = 30
            badge4.maxCharacterCount = 3
            //用于设置/获取它可以是徽章的严重性TOP_END，TOP_START，BOTTOM_END或BOTTOM_START。默认值为TOP_END
            badge4.badgeGravity = BadgeDrawable.TOP_START

            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> binding.viewPager.currentItem = 0
                    R.id.menu_system -> binding.viewPager.currentItem = 1
                    R.id.menu_official_account -> binding.viewPager.currentItem = 2
                    R.id.menu_navigation -> binding.viewPager.currentItem = 3
                    R.id.menu_project -> binding.viewPager.currentItem = 4
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
    }

    private fun showUserInfo(coinInfo: CoinInfo?) {
        tvUserId.text = coinInfo?.userId.toString()
        tvUserName.text = coinInfo?.username ?: getString(com.wan.login.R.string.go_login)
        tvUserGrade.text = coinInfo?.level?.toString() ?: getString(R.string.nav_line_2)
        tvUserRank.text = coinInfo?.rank?.toString() ?: getString(R.string.nav_line_2)
        logoutMenuItem.isVisible = (coinInfo != null)
    }

    private fun receiveNotice() {
        LiveEventBus.get(com.wan.common.constant.Const.THEME_COLOR, Int::class.java)
            .observe(this) { setThemeColor() }
    }

    private fun setThemeColor() {
        navHeaderView.backgroundColor = getThemeColor()
    }

    override fun startObserve() {
        mainViewModel.uiState.observe(this) {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { userInfoEntity ->
                showUserInfo(userInfoEntity.coinInfo)
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        }

        accountViewModel.uiState.observe(this) {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { showUserInfo(null) }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        }

        accountViewModel.isLogged.observe(this) {
            mainViewModel.getUserInfo()
        }
    }
}
