package com.qh.wanandroid.app

import com.didichuxing.doraemonkit.DoraemonKit
import com.example.devlibrary.app.App
import com.qh.wanandroid.koin.appModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Description: application
 * Created by FQH on 2019/10/21.
 */
class DevApplication : App() {
    override fun onCreate() {
        super.onCreate()
        DoraemonKit.install(this)

        startKoin {
            androidContext(this@DevApplication)
            modules(appModule)
        }
        Realm.init(this)
    }
}