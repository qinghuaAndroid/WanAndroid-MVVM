package com.wan.baselib.ext

import com.wan.baselib.R
import com.wan.baselib.utils.ResourcesUtils
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.ToastUtils

/**
 * toast
 */

fun showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun showLongToast(content: String) {
    ToastUtils.showLongToast(content)
}

fun getThemeColor() = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor()
} else {
    ResourcesUtils.getColor(R.color.colorPrimary)
}
