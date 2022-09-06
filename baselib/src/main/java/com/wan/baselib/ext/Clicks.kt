package com.wan.baselib.ext

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

/**
 * Created by Cy on 25/6/2021.
 */

inline fun <reified T : View> T.onThrottledClick(crossinline onClick: (view: View) -> Unit) {
    setOnClickListener {
        isClickable = false
        onClick(it)
        postDelayed({ isClickable = true }, 1000)
    }
}

inline fun <reified T : View> T.onRxClick(crossinline onClick: (view: T) -> Unit) {
    this.clicks().subscribe {
        onClick(this)
    }
}

inline fun <reified T : View> T.onRxThrottledClick(crossinline onClick: (view: T) -> Unit) {
    this.clicks().throttleFirst(1, TimeUnit.SECONDS)
        .subscribe {
            onClick(this)
        }
}
