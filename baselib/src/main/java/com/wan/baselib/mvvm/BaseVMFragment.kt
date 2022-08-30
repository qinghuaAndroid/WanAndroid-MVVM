package com.wan.baselib.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.wan.baselib.base.BaseFragment

/**
 * @author cy
 * Create at 2020/4/3.
 */
abstract class BaseVMFragment<VM : BaseViewModel, B : ViewDataBinding> : BaseFragment<B>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    abstract fun subscribeUi()

    open fun showProgressDialog() {

    }

    open fun dismissProgressDialog() {

    }
}