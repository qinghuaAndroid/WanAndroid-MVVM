package com.wan.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle4.components.support.RxFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by Cy on 2018/9/27.
 */
abstract class BaseFragment<B : ViewDataBinding> : RxFragment(), CoroutineScope by MainScope() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (attachLayoutRes() == 0) {
            throw RuntimeException("Please set the page layout")
        }
        binding = DataBindingUtil.inflate(inflater, attachLayoutRes(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        subscribeEvent()
    }

    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initData(savedInstanceState: Bundle?)
    protected abstract fun initView(view: View)
    protected abstract fun loadData()
    protected open fun subscribeEvent() {}

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}