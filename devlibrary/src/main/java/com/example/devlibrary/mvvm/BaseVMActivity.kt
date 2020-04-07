package com.example.devlibrary.mvvm

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.devlibrary.base.BaseActivity

/**
 * @author FQH
 * Create at 2020/4/3.
 */
abstract class BaseVMActivity<VM : BaseViewModel, B : ViewDataBinding> : BaseActivity<B>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
    }

    abstract fun startObserve()

    open fun showProgressDialog() {

    }

    open fun dismissProgressDialog() {

    }
}