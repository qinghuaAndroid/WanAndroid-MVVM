package com.wan.baselib.ext

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

/**
 * Created by Cy on 25/6/2021.
 */

inline fun android.view.View.onSingleClick(crossinline onClick: (view: View) -> Unit) {
    setOnClickListener {
        isClickable = false
        onClick(it)
        postDelayed({ isClickable = true }, 1000)
    }
}

inline fun android.view.View.onClick(crossinline onClick: (view: View) -> Unit) {
    setOnClickListener {
        onClick(it)
    }
}

inline fun <T : View> T.onRxSingleClick(crossinline onClick: (view: T) -> Unit) {
    this.clicks().throttleFirst(1, TimeUnit.SECONDS)
        .subscribe {
            onClick(this)
        }
}

inline fun <T : View> T.onRxClick(crossinline onClick: (view: T) -> Unit) {
    this.clicks().subscribe {
        onClick(this)
    }
}