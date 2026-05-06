package com.wan.baselib.base

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.Insets
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.wan.baselib.R
import com.wan.baselib.ext.actionBarHeight
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
    CoroutineScope by MainScope(), OnApplyWindowInsetsListener {
    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        AutoDensityUtils.setCustomDensity(this, application)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this //xml中若有使用livedata
        enableEdgeToEdge()
        // 通过该监听可实时感知状态栏、导航栏、键盘高度
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView, this)
        initToolbar()
        initData(savedInstanceState)
        initView()
        subscribeEvent()
        loadData()
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val insets1 = insets.getInsets(WindowInsetsCompat.Type.ime())
        println("软键盘: ${insets1.left}, ${insets1.top}, ${insets1.right}, ${insets1.bottom}")
        val insets2 = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        println("导航栏: ${insets2.left}, ${insets2.top}, ${insets2.right}, ${insets2.bottom}")
        val insets3 = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        println("状态栏: ${insets3.left}, ${insets3.top}, ${insets3.right}, ${insets3.bottom}")
        val insets4 = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        println("系统栏: ${insets4.left}, ${insets4.top}, ${insets4.right}, ${insets4.bottom}")
        val insets5 = Insets.max(insets1, insets4)
        println("最终: ${insets5.left}, ${insets5.top}, ${insets5.right}, ${insets5.bottom}")
        dispatchWindowInsets(insets5)
        return WindowInsetsCompat.CONSUMED
    }

    protected open fun dispatchWindowInsets(insets: Insets) {
        val findViewById = findViewById<Toolbar>(R.id.toolbar)
        findViewById?.let {
            it.updateLayoutParams { height = actionBarHeight + insets.top }
            it.updatePadding(top = insets.top)
        }
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

    private fun initToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.let {
            it.title = ""
            setSupportActionBar(it)
            it.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
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
            this.supportActionBar?.setBackgroundDrawable(themeColor.toDrawable())
        }
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initData(savedInstanceState: Bundle?)
    protected abstract fun initView()
    protected open fun subscribeEvent() {}

    protected abstract fun loadData()

    override fun onDestroy() {
        super.onDestroy()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView, null)
        binding.unbind()
        cancel()
    }
}