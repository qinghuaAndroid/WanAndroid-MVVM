package com.wan.android.provider

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoKit
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import com.wan.android.BuildConfig
import com.wan.baselib.log.LogUtil
import com.wan.baselib.storage.StorageType
import com.wan.baselib.storage.StorageUtil
import com.wan.baselib.utils.AutoDensityUtils
import com.wan.baselib.utils.SettingUtil
import com.wan.common.callback.EmptyCallback
import com.wan.common.callback.ErrorCallback
import com.wan.common.callback.LoadingCallback
import com.wan.common.callback.TimeoutCallback


/**
 * Created by cy on 28/6/2021.
 */
class InitializationProvider : Initializer<Unit> {
    override fun create(context: Context) {
        AutoDensityUtils.init()
        MMKV.initialize(context)
        DoKit.Builder(context as Application)
            .build()
        initARouter(context)
        initTheme()
        initLoadSir()
        LiveEventBus.config()
            .lifecycleObserverAlwaysActive(false)
            .autoClear(true)
        //初始化项目一些图片、视频、日志等文件存储目录
        StorageUtil.init(context, context.externalCacheDir?.canonicalPath)
        //初始日志文件存储目录
        val directory = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG)
        LogUtil.init(directory, Log.DEBUG)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    private fun initARouter(context: Application) {
        if (BuildConfig.DEBUG) {    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context) // 尽可能早，推荐在Application中初始化
    }

    private fun initTheme() {
        // 获取当前的主题
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}