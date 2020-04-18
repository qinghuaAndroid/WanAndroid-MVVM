package com.example.devlibrary.ext

import android.content.Context
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

fun getThemeColor(context: Context) = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor(context)
} else {
    ResourcesUtils.getColor(context, R.color.colorPrimary)
}
