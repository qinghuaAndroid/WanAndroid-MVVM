package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer
import com.kingja.loadsir.core.LoadSir
import com.wan.common.callback.EmptyCallback
import com.wan.common.callback.ErrorCallback
import com.wan.common.callback.LoadingCallback
import com.wan.common.callback.TimeoutCallback

/**
 *
 * Created by cy on 2023/7/18.
 */
class LoadSirInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(ThemeInitializer::class.java)
    }
}