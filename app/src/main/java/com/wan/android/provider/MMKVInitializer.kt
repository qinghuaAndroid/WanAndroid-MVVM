package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV

/**
 *
 * Created by cy on 2023/7/18.
 */
class MMKVInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        MMKV.initialize(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AutoSizeInitializer::class.java)
    }
}