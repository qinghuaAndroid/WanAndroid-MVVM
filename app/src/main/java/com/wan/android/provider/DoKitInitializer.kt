package com.wan.android.provider

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.didichuxing.doraemonkit.DoKit

/**
 *
 * Created by cy on 2023/7/18.
 */
class DoKitInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        DoKit.Builder(context as Application)
            .build()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LogInitializer::class.java)
    }
}