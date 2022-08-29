package com.wan.android.ui.main

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wan.android.R
import com.wan.android.bean.CoinInfo
import com.wan.android.databinding.ActivityMainBinding
import com.wan.android.databinding.NavHeaderMainBinding
import com.wan.android.ui.account.AccountViewModel
import com.wan.baselib.ext.getThemeColor
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.utils.SettingUtil
import com.wan.common.arouter.ArouterPath
import com.wan.common.ext.navigation
import dagger.hilt.android.AndroidEntryPoint
import splitties.views.backgroundColor
import splitties.views.onClick


@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_MAIN)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {

    private val accountViewModel by viewModels<AccountViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        receiveNotice()
    }

    override fun initView() {
        initDrawerLayout()
        initNavigation()
        initNavView()
        setThemeColor()
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

    private fun initNavigation() {
        val navView: BottomNavigationView = binding.btmNavigation
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHost.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val topLevelDestinationIds = setOf(
            R.id.navigation_home,
            R.id.navigation_system,
            R.id.navigation_official,
            R.id.navigation_navigation,
            R.id.navigation_project
        )
        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        navHeaderMainBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        binding.navView.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        navHeaderMainBinding.tvUsername.onClick {
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
            val badge = getOrCreateBadge(R.id.navigation_home)
            badge.clearNumber()

            val badge1 = getOrCreateBadge(R.id.navigation_system)
            badge1.number = 2

            val badge2 = getOrCreateBadge(R.id.navigation_official)
            badge2.number = 100
            //用于设置/获取徽章数字中允许的最大字符数，然后将其用'+'截断。预设值为4。
            badge2.maxCharacterCount = 3

            val badge3 = getOrCreateBadge(R.id.navigation_navigation)
            badge3.clearNumber()

            val badge4 = getOrCreateBadge(R.id.navigation_project)
            badge4.number = 30
            badge4.maxCharacterCount = 3
            //用于设置/获取它可以是徽章的严重性TOP_END，TOP_START，BOTTOM_END或BOTTOM_START。默认值为TOP_END
            badge4.badgeGravity = BadgeDrawable.TOP_START
        }
    }

    private fun showUserInfo(coinInfo: CoinInfo?) {
        navHeaderMainBinding.data = coinInfo
        binding.navView.menu.findItem(R.id.nav_logout).isVisible = (coinInfo != null)
    }

    private fun receiveNotice() {
        LiveEventBus.get(com.wan.common.constant.Const.THEME_COLOR, Int::class.java)
            .observe(this) { setThemeColor() }
    }

    private fun setThemeColor() {
        navHeaderMainBinding.root.backgroundColor = getThemeColor()
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
