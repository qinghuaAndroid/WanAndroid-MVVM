package com.wan.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.common.constant.Const
import com.wan.login.databinding.FragmentLoginBinding
import com.wan.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/6/23.
 */
@AndroidEntryPoint
class LoginFragment : BaseVMFragment<LoginViewModel, FragmentLoginBinding>() {

    private val mViewModel by viewModels<LoginViewModel>()
    private var isPasswordShow = false

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner) {
            if (it.showProgress) showProgressDialog()
            it.showSuccess?.let {
                LiveEventBus.get<Boolean>(Const.LOGIN_SUCCESS).post(true)
                dismissProgressDialog()
                activity?.finish()
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
            }
        }
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_login

    override fun initData() {

    }

    override fun initView(view: View) {
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        binding.tvRegister.setOnClickListener {
            val direction =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment(mViewModel.userName.get())
            Navigation.findNavController(it).navigate(direction)
            //不传参数可用下面这种
//            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.ivClear.onClick {
            binding.etUsername.requestFocus()
            binding.etUsername.setText("")
        }
        binding.ivPasswordVisibility.onClick {
            binding.etPassword.requestFocus()
            binding.etPassword.transformationMethod = if (isPasswordShow) {
                isPasswordShow = false
                //显示密码状态
                binding.ivPasswordVisibility.setImageResource(R.mipmap.password_show)
                PasswordTransformationMethod.getInstance()

            } else {
                isPasswordShow = true
                //显示明文状态,手动将光标移到最后
                binding.ivPasswordVisibility.setImageResource(R.mipmap.password_hide)
                HideReturnsTransformationMethod.getInstance()
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
        binding.tvSkip.onClick { activity?.finish() }
    }

    override fun loadData() {

    }
}