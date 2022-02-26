package com.wan.android.ui.splash

import android.Manifest
import com.alibaba.android.arouter.launcher.ARouter
import com.wan.android.R
import com.wan.android.databinding.ActivitySplashBinding
import com.tbruyelle.rxpermissions3.RxPermissions
import com.wan.baselib.base.BaseActivity
import com.wan.baselib.utils.ToastUtils
import com.wan.common.arouter.ArouterPath
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author cy
 * Create at 2020/4/2.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

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