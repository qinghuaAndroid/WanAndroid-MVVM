package com.example.devlibrary.ext

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.devlibrary.R
import com.example.devlibrary.utils.ResourcesUtils
import com.example.devlibrary.utils.SettingUtil
import com.example.devlibrary.utils.ToastUtils

/**
 * toast
 */
fun Fragment.showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun Context.showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun Fragment.getThemeColor() = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor()
} else {
    ResourcesUtils.getColor(R.color.colorPrimary)
}

fun Context.getThemeColor() = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor()
} else {
    ResourcesUtils.getColor(R.color.colorPrimary)
}
