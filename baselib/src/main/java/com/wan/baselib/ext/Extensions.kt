package com.wan.baselib.ext

import com.wan.baselib.R
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.ToastUtils
import splitties.init.appCtx
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
    appCtx.color(R.color.Grey800)
} else {
    SettingUtil.getColor()
}