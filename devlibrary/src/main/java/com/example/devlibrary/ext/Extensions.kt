package com.example.devlibrary.ext

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.devlibrary.utils.ToastUtils

/**
 * Log
 */
fun Any.loge(content: String?) {
    loge(this.javaClass.simpleName, content ?: "")
}

fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: "")
}

fun Fragment.showToast(content: String) {
    ToastUtils.showShortToast(content)
}

fun Context.showToast(content: String) {
    ToastUtils.showShortToast(content)
}