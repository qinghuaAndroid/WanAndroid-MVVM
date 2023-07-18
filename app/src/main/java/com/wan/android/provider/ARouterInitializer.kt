package com.wan.android.provider

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.wan.android.BuildConfig

/**
 *
 * Created by cy on 2023/7/18.
 */
class ARouterInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context as Application) // 尽可能早，推荐在Application中初始化
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(MMKVInitializer::class.java)
    }
}