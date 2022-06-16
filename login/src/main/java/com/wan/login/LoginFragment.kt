package com.wan.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.login.databinding.FragmentLoginBinding
import com.wan.login.viewmodel.LoginViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/6/23.
 */
class LoginFragment : BaseVMFragment<LoginViewModel, FragmentLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModel()
    private var isPasswordShow = false

    override fun startObserve() {
        mViewModel.uiState.observe(this,
            Observer {
                if (it.showProgress) showProgressDialog()
                it.showSuccess?.let {
                    LiveEventBus.get(Const.LOGIN_SUCCESS).post(true)
                    dismissProgressDialog()
                    activity?.finish()
                }
                it.showError?.let { errorMsg ->
                    showToast(errorMsg)
                }
            })
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_login

    override fun initData() {

    }

    override fun initView(view: View) {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        mBinding.tvRegister.setOnClickListener {
            val direction =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment(mViewModel.userName.get())
            Navigation.findNavController(it).navigate(direction)
            //不传参数可用下面这种
//            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

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
        mBinding.tvSkip.onClick { activity?.finish() }
    }

    override fun loadData() {

    }
}