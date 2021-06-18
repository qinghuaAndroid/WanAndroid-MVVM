package com.wan.baselib.mvp

import android.view.View
import androidx.databinding.ViewDataBinding
import com.wan.baselib.base.BaseFragment
import com.wan.baselib.ext.showToast

/**
 * @author chenxz
 * @date 2018/9/7
 * @desc BaseMvpFragment
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>, B : ViewDataBinding> :
    BaseFragment<B>(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String?) {
        errorMsg?.let { showToast(it) }
    }

    override fun showError(errorCode: Int, errorMsg: String?) {
        errorMsg?.let { showToast(it) }
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

}