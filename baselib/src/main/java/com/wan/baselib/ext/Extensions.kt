package com.wan.baselib.ext

import android.content.Context
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

fun getThemeColor(context: Context) = if (!SettingUtil.getIsNightMode()) {
    SettingUtil.getColor(context)
} else {
    ResourcesUtils.getColor(context, R.color.colorPrimary)
}
