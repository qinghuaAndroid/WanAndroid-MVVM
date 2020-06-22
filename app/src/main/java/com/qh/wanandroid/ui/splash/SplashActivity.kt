package com.qh.wanandroid.ui.splash

import android.Manifest
import com.alibaba.android.arouter.launcher.ARouter
import com.example.devlibrary.base.BaseActivity
import com.example.devlibrary.utils.ToastUtils
import com.qh.wanandroid.R
import com.example.common.arouter.ArouterPath
import com.qh.wanandroid.databinding.ActivitySplashBinding
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun attachLayoutRes(): Int = R.layout.activity_splash

    override fun initData() {
        val disposable = RxPermissions(this).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .compose(this.bindToLifecycle())
            .subscribe { granted ->
                if (granted) {
                    enterMain()
                } else {
                    ToastUtils.showLongToast("请授予应用权限以获得更好的使用体验")
                }
            }
    }

    private fun enterMain() {
        GlobalScope.launch {
            delay(2000)
            ARouter.getInstance().build(ArouterPath.ACTIVITY_MAIN).navigation()
            finish()
        }
    }

    override fun initView() {

    }

    override fun loadData() {

    }
}