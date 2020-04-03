package com.qh.wanandroid.app

import com.didichuxing.doraemonkit.DoraemonKit
import com.example.devlibrary.app.App

/**
 * Description: application
 * Created by FQH on 2019/10/21.
 */
class DevApplication : App() {
    override fun onCreate() {
        super.onCreate()
        DoraemonKit.install(this)
    }
}