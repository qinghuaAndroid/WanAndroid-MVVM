package com.wan.login.ui

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.baselib.base.BaseActivity
import com.wan.common.arouter.ArouterPath
import com.wan.login.R
import com.wan.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author cy
 * Create at 2020/4/7.
 * @see <a href="https://mp.weixin.qq.com/s/kqjYN4LqzONkDAHI3rLRTQ">navigation</a>
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_login)
        val navController = (fragment as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(setOf())
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun loadData() {

    }

    override fun initData() {

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_login)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}