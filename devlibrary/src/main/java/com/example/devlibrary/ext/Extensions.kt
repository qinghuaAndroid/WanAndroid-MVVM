package com.example.devlibrary.ext

import com.example.devlibrary.R
import com.example.devlibrary.utils.ResourcesUtils
import com.example.devlibrary.utils.SettingUtil
import com.example.devlibrary.utils.ToastUtils

/**
 * toast
 */

fun showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun getThemeColor() = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor()
} else {
    ResourcesUtils.getColor(R.color.colorPrimary)
}
