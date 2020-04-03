package com.example.devlibrary.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.devlibrary.base.BaseFragment

/**
 * @author FQH
 * Create at 2020/4/3.
 */
abstract class BaseVMFragment<VM : BaseViewModel, B : ViewDataBinding> : BaseFragment<B>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserve()
    }

    abstract fun startObserve()

}