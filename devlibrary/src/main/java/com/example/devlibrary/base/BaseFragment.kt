package com.example.devlibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by Cy on 2018/9/27.
 */
abstract class BaseFragment<B : ViewDataBinding?> : RxFragment(), CoroutineScope by MainScope() {
    protected var mBinding: B? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (attachLayoutRes() != 0) {
            mBinding = DataBindingUtil.inflate<B>(inflater, attachLayoutRes(), container, false)
        }
        return mBinding!!.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView(view)
        loadData()
    }

    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initData()
    protected abstract fun initView(view: View)
    protected abstract fun loadData()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}