package com.example.devlibrary.utils

import com.example.devlibrary.app.App

/**
 * @author FQH
 * Create at 2020/4/11.
 */

fun dp2px(dp: Int): Int {
    val scale = App.sContext.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2dp(px: Int): Int {
    val scale = App.sContext.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun <T> Any?.notNull(f: () -> T, t: () -> T): T {
    return if (this != null) f() else t()
}

fun <T> Any?.notNull(f: () -> Unit, t: () -> Unit) {
    if (this != null) f() else t()
}