package com.wan.baselib.ext

import com.wan.baselib.R
import com.wan.baselib.app.App
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.ToastUtils
import splitties.resources.color

/**
 * toast
 */

fun showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun showLongToast(content: String) {
    ToastUtils.showLongToast(content)
}

fun getThemeColor() = if (SettingUtil.getIsNightMode()) {
    splitties.init.appCtx.color(R.color.Grey800)
} else {
    SettingUtil.getColor()
}