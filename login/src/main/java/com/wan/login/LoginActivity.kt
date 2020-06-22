package com.wan.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.arouter.ArouterPath
import com.example.common.constant.Const
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvvm.BaseVMActivity
import com.example.devlibrary.utils.ToastUtils
import com.wan.login.databinding.ActivityLoginBinding
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/7.
 */
@Route(path = ArouterPath.ACTIVITY_LOGIN)
class LoginActivity : BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModel()
    private var isPasswordShow = false

    override fun startObserve() {
        mViewModel.uiState.observe(this,
            Observer {
                if (it.showProgress) showProgressDialog()
                it.showSuccess?.let {
                    LiveEventBusHelper.post(Const.LOGIN_SUCCESS, true)
                    dismissProgressDialog()
                    finish()
                }
                it.showError?.let { errorMsg ->
                    ToastUtils.showShortToast(errorMsg)
                }
            })
    }

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun initData() {

    }

    override fun initView() {
        title = getString(R.string.login)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        mBinding.ivClear.onClick {
            mBinding.etUsername.requestFocus()
            mBinding.etUsername.setText("")
        }
        mBinding.ivPasswordVisibility.onClick {
            mBinding.etPassword.requestFocus()
            mBinding.etPassword.transformationMethod = if (isPasswordShow) {
                isPasswordShow = false
                //显示密码状态
                mBinding.ivPasswordVisibility.setImageResource(R.mipmap.password_show)
                PasswordTransformationMethod.getInstance()

            } else {
                isPasswordShow = true
                //显示明文状态,手动将光标移到最后
                mBinding.ivPasswordVisibility.setImageResource(R.mipmap.password_hide)
                HideReturnsTransformationMethod.getInstance()
            }
            mBinding.etPassword.setSelection(mBinding.etPassword.text.length)
        }
    }

    override fun loadData() {

    }

}