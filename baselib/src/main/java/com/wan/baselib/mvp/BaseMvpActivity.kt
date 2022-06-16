package com.wan.baselib.mvp

import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.ViewDataBinding
import com.wan.baselib.base.BaseActivity
import com.wan.baselib.ext.showToast

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
    private var progressBar:ContentLoadingProgressBar? = null

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
        if (progressBar == null){
            progressBar = ContentLoadingProgressBar(this)
        }
        progressBar?.show()
    }

    override fun hideLoading() {
        progressBar?.hide()
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