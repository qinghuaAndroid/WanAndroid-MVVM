package com.example.devlibrary.ext

import android.content.res.Resources
import com.example.devlibrary.R
import com.example.devlibrary.utils.SettingUtil
import com.example.devlibrary.utils.ToastUtils

/**
 * toast
 */

fun showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun getThemeColor(resources: Resources) = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor()
} else {
    resources.getColor(R.color.colorPrimary)
}
