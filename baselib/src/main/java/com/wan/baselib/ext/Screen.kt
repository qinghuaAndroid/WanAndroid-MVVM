package com.wan.baselib.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

val Context.actionBarHeight: Int
    get() {
        val outValue = TypedValue()
        return if (theme.resolveAttribute(android.R.attr.actionBarSize, outValue, true)) {
            TypedValue.complexToDimensionPixelSize(
                outValue.data,
                resources.displayMetrics
            )
        } else 0
    }

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()