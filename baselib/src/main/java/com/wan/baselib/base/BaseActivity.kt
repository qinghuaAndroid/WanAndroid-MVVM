package com.wan.baselib.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.wan.baselib.R
import com.wan.baselib.ext.getThemeColor
import com.wan.baselib.utils.AutoDensityUtils
import com.wan.baselib.utils.StatusBarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *
 * @author Cy
 * @date 2018/9/20
 */
abstract class BaseActivity<B : ViewDataBinding> : RxAppCompatActivity(),
    CoroutineScope by MainScope() {
    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        AutoDensityUtils.setCustomDensity(this, application)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this //xml中若有使用livedata
        initToolbar()
        initData(savedInstanceState)
        initView()
        subscribeEvent()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

    private fun initToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.let {
            it.title = ""
            setSupportActionBar(it)
            it.setNavigationOnClickListener { onBackPressed() }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setTitle(charSequence: CharSequence) {
        findViewById<Toolbar>(R.id.toolbar)?.title = charSequence
    }

    override fun setTitle(@StringRes resId: Int) {
        findViewById<Toolbar>(R.id.toolbar)?.setTitle(resId)
    }

    open fun initColor() {
        val themeColor = getThemeColor()
        StatusBarUtil.setColor(this, themeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(themeColor))
        }
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initData(savedInstanceState: Bundle?)
    protected abstract fun initView()
    protected open fun subscribeEvent() {}
    protected abstract fun loadData()

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        cancel()
    }
}