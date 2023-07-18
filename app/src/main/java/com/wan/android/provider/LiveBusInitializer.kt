package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * Created by cy on 2023/7/18.
 */
class LiveBusInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        LiveEventBus.config()
            .lifecycleObserverAlwaysActive(false)
            .autoClear(true)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LoadSirInitializer::class.java)
    }
}