package com.wan.android.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.wan.android.R
import com.wan.android.databinding.ActivitySplashBinding
import com.wan.baselib.base.BaseActivity
import com.wan.baselib.ext.showLongToast
import com.wan.common.arouter.ArouterPath
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author cy
 * Create at 2020/4/2.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.forEach { (t, u) ->
                    println("$t: $u")
                }
                if (permissions.values.none { !it }) {
                    enterMain()
                } else {
                    showLongToast("请授予应用权限以获得更好的使用体验")
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                )
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                )
            )
        } else {
            launcher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    private fun enterMain() {
        lifecycleScope.launch {
            delay(2000)//delay函数是挂起，非阻塞的
            ARouter.getInstance().build(ArouterPath.ACTIVITY_MAIN).navigation()
            finish()
        }
    }

    override fun initView() {

    }

    override fun loadData() {

    }
}