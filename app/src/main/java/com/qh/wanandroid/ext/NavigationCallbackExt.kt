package com.qh.wanandroid.ext

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback

/**
 * @author FQH
 * Create at 2020/4/22.
 */

fun Postcard.navigation(context: Context, callback: KtxNavigationCallback.() -> Unit): Any? =
    navigation(context, KtxNavigationCallback().apply(callback))

class KtxNavigationCallback : NavigationCallback {

    private var _onLost: ((Postcard?) -> Unit)? = null
    private var _onFound: ((Postcard?) -> Unit)? = null
    private var _onInterrupt: ((Postcard?) -> Unit)? = null
    private var _onArrival: ((Postcard?) -> Unit)? = null

    fun onLost(listener: ((Postcard?) -> Unit)) {
        _onLost = listener
    }

    fun onFound(listener: ((Postcard?) -> Unit)) {
        _onFound = listener
    }

    fun onInterrupt(listener: ((Postcard?) -> Unit)) {
        _onInterrupt = listener
    }

    fun onArrival(listener: ((Postcard?) -> Unit)) {
        _onArrival = listener
    }

    override fun onLost(postcard: Postcard?) {
        _onLost?.invoke(postcard)
    }

    override fun onFound(postcard: Postcard?) {
        _onFound?.invoke(postcard)
    }

    override fun onInterrupt(postcard: Postcard?) {
        _onInterrupt?.invoke(postcard)
    }

    override fun onArrival(postcard: Postcard?) {
        _onArrival?.invoke(postcard)
    }

}