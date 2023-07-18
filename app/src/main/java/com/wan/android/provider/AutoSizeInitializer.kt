package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer
import com.wan.baselib.utils.AutoDensityUtils

/**
 *
 * Created by cy on 2023/7/18.
 */
class AutoSizeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AutoDensityUtils.init()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}