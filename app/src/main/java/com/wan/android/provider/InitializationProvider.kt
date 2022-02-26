package com.wan.android.provider

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.tencent.mmkv.MMKV
import com.wan.android.BuildConfig
import com.wan.android.koin.appModule
import com.wan.baselib.utils.AutoDensityUtils
import com.wan.baselib.utils.SettingUtil
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by cy on 28/6/2021.
 */
class InitializationProvider : Initializer<Unit> {
    override fun create(context: Context) {
        AutoDensityUtils.init()
        MMKV.initialize(context)
        DoraemonKit.install(context as Application)//context is a Application
        initKoin(context)
        Realm.init(context)
        initARouter(context)
        initTheme()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    private fun initKoin(context: Context) {
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    private fun initARouter(context: Application) {
        if (BuildConfig.DEBUG) {    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context); // 尽可能早，推荐在Application中初始化
    }

    private fun initTheme() {
        // 获取当前的主题
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}