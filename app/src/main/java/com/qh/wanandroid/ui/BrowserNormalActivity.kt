package com.qh.wanandroid.ui

import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.example.common.constant.Const
import com.example.devlibrary.base.BaseActivity
import com.just.agentweb.*
import com.qh.wanandroid.R
import com.qh.wanandroid.databinding.ActivityBrowserNormalBinding

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class BrowserNormalActivity : BaseActivity<ActivityBrowserNormalBinding>() {

    private lateinit var title: String
    private lateinit var url: String
    private lateinit var mAgentWeb: AgentWeb
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override fun attachLayoutRes(): Int = R.layout.activity_browser_normal

    override fun initData() {
        title = intent.getStringExtra(Const.WEB_TITLE)
        url = intent.getStringExtra(Const.WEB_URL)
    }

    override fun initView() {
        setTitle(this@BrowserNormalActivity.title)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebView(mWebView)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted")
        }
    }

    private val mWebChromeClient: WebChromeClient =
        object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                //此处可获取网页标题
            }
        }

    override fun loadData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        Log.i("Info", "onResult:$requestCode onResult:$resultCode")
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        //mAgentWeb.destroy();
        mAgentWeb.webLifeCycle.onDestroy()
    }
}