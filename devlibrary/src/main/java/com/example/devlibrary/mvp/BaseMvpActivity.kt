package com.example.devlibrary.mvp

import androidx.databinding.ViewDataBinding
import com.example.devlibrary.base.BaseActivity
import com.example.devlibrary.ext.showToast

/**
 * @author chenxz
 * @date 2018/9/7
 * @desc BaseMvpActivity
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>, B : ViewDataBinding> :
    BaseActivity<B>(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
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