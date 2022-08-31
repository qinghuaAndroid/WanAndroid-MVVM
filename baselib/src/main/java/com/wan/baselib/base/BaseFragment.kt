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
    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
        subscribeEvent()
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
        _binding = DataBindingUtil.inflate(inflater, attachLayoutRes(), container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner //xml中若有使用livedata
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initData(savedInstanceState: Bundle?)
    protected abstract fun initView(view: View)
    protected abstract fun loadData()
    protected open fun subscribeEvent() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}