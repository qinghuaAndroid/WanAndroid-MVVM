package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer


/**
 * Created by cy on 28/6/2021.
 */
class WanAppInitializer : Initializer<Unit> {
    override fun create(context: Context) {

    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DoKitInitializer::class.java)
    }
}