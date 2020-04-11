package com.example.devlibrary.ext

import android.content.Context
import androidx.fragment.app.Fragment
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