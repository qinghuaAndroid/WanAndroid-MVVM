package com.example.devlibrary.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.devlibrary.app.App
import com.example.devlibrary.ext.getThemeColor
import com.example.devlibrary.utils.AutoDensityUtils
import com.example.devlibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *
 * @author Cy
 * @date 2018/9/20
 */
abstract class BaseActivity<B : ViewDataBinding> : RxActivity(), CoroutineScope by MainScope() {
    protected lateinit var mBinding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        AutoDensityUtils.setCustomDensity(this, App.sInstance)
        super.onCreate(savedInstanceState)
        if (attachLayoutRes() == 0) {
            throw RuntimeException("Please set the page layout")
        }
        mBinding = DataBindingUtil.setContentView<B>(this, attachLayoutRes())
        initData()
        initToolbar()
        initView()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

    private fun initToolbar() {
        toolbar?.let {
            it.title = ""
            setSupportActionBar(it)
            it.setNavigationOnClickListener { onBackPressed() }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setTitle(charSequence: CharSequence) {
        toolbar?.title = charSequence
    }

    override fun setTitle(@StringRes resId: Int) {
        toolbar?.setTitle(resId)
    }

    open fun initColor() {
        val themeColor = getThemeColor(resources)
        StatusBarUtil.setColor(this, themeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(themeColor))
        }
    }


    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initData()
    protected abstract fun initView()
    protected abstract fun loadData()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}