package com.wan.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import com.example.devlibrary.base.IFragmentCallBack
import com.example.devlibrary.mvvm.BaseVMFragment
import com.example.devlibrary.utils.ToastUtils
import com.wan.login.databinding.FragmentRegisterBinding
import com.wan.login.viewmodel.RegisterViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/6/23.
 */
class RegisterFragment(callBack: IFragmentCallBack) : BaseVMFragment<RegisterViewModel, FragmentRegisterBinding>() {

    private val mViewModel: RegisterViewModel by viewModel()
    private var isPasswordShow = false
    private var isRePasswordShow = false
    private val iCallBack = callBack

    override fun startObserve() {
        mViewModel.uiState.observe(this,
            Observer {
                if (it.showProgress) showProgressDialog()
                it.showSuccess?.let {
                    dismissProgressDialog()
                    iCallBack.home()
                }
                it.showError?.let { errorMsg ->
                    ToastUtils.showShortToast(errorMsg)
                }
            })
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_register

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        mBinding.tvRegister.onClick { iCallBack.home() }
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
        mBinding.ivRePasswordVisibility.onClick {
            mBinding.etRePassword.requestFocus()
            mBinding.etRePassword.transformationMethod = if (isRePasswordShow) {
                isRePasswordShow = false
                //显示密码状态
                mBinding.ivRePasswordVisibility.setImageResource(R.mipmap.password_show)
                PasswordTransformationMethod.getInstance()

            } else {
                isRePasswordShow = true
                //显示明文状态,手动将光标移到最后
                mBinding.ivRePasswordVisibility.setImageResource(R.mipmap.password_hide)
                HideReturnsTransformationMethod.getInstance()
            }
            mBinding.etRePassword.setSelection(mBinding.etRePassword.text.length)
        }
    }

    override fun loadData() {

    }
}