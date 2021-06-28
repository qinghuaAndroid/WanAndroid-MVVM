package com.wan.android.app

import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.wan.baselib.app.App
import com.wan.android.BuildConfig
import com.wan.android.koin.appModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Description: application
 * Created by FQH on 2019/10/21.
 */
class WanApplication : App() {
    override fun onCreate() {
        super.onCreate()
        DoraemonKit.install(this)

        startKoin {
            androidContext(this@WanApplication)
            modules(appModule)
        }
        Realm.init(this)
        initArouter()
    }

    private fun initArouter() {
        if (BuildConfig.DEBUG) {    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}