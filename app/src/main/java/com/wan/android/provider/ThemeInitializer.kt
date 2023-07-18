package com.wan.android.provider

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.Initializer
import com.wan.baselib.utils.SettingUtil

/**
 *
 * Created by cy on 2023/7/18.
 */
class ThemeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        // 获取当前的主题
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(ARouterInitializer::class.java)
    }
}