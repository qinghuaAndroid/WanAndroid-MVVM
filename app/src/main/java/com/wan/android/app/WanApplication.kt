package com.wan.android.app

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.wan.baselib.app.App
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by cy on 2019/10/21.
 *
 * 初始化操作使用jetpack-startup
 * [com.wan.android.provider.InitializationProvider]
 */
@HiltAndroidApp
class WanApplication : App(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()

        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker())
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

}