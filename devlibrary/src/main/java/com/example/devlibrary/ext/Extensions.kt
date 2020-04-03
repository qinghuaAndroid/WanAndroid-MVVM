package com.example.devlibrary.ext

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.devlibrary.R
import com.example.devlibrary.utils.ToastUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient

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

/**
 * getAgentWeb
 */
fun String.getAgentWeb(
        activity: Activity,
        webContent: ViewGroup,
        layoutParams: ViewGroup.LayoutParams,
        webView: WebView,
        webChromeClient: WebChromeClient?,
        webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
        .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
        .useDefaultIndicator()// 使用默认进度条
        .setWebView(webView)
        .setWebChromeClient(webChromeClient)
        .setWebViewClient(webViewClient)
        .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
        .createAgentWeb()//
        .ready()
        .go(this)