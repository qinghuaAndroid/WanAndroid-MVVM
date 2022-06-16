package com.wan.baselib.mvvm

import android.os.Bundle
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.ViewDataBinding
import com.wan.baselib.base.BaseActivity

/**
 * @author cy
 * Create at 2020/4/3.
 */
abstract class BaseVMActivity<VM : BaseViewModel, B : ViewDataBinding> : BaseActivity<B>() {

    private var progressBar: ContentLoadingProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
    }

    abstract fun startObserve()

    open fun showProgressDialog() {
        if (progressBar == null) {
            progressBar = ContentLoadingProgressBar(this)
        }
        progressBar?.show()
    }

    open fun dismissProgressDialog() {
        progressBar?.hide()
    }
}