package com.example.devlibrary.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.devlibrary.app.App
import com.example.devlibrary.utils.AutoDensityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *
 * @author Cy
 * @date 2018/9/20
 */
abstract class BaseActivity<B : ViewDataBinding?> : RxActivity(), CoroutineScope by MainScope() {
    protected var mBinding: B? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        AutoDensityUtils.setCustomDensity(this, App.sInstance)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<B>(this, attachLayoutRes())
        initData()
        initView()
        loadData()
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