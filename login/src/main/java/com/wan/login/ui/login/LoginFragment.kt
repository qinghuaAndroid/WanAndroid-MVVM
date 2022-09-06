package com.wan.login.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wan.baselib.ext.onThrottledClick
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.login.R
import com.wan.login.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author FQH
 * Create at 2020/6/23.
 */
@AndroidEntryPoint
class LoginFragment : BaseVMFragment<LoginViewModel, FragmentLoginBinding>() {

    private val mViewModel by viewModels<LoginViewModel>()
    private var isPasswordShow = false

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.navigateToRegister.collect {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment(
                        mViewModel.userName.get()
                    )
                )
            }
        }
        mViewModel.uiState.observe(viewLifecycleOwner) {
            if (it.showProgress) showProgressDialog()
            it.showSuccess?.let {
                dismissProgressDialog()
                activity?.finish()
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
            }
        }
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_login

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(view: View) {
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        binding.tvRegister.onThrottledClick {
            mViewModel.userClicksOnButton()
        }

        binding.ivClear.onThrottledClick {
            binding.etUsername.requestFocus()
            binding.etUsername.setText("")
        }
        binding.ivPasswordVisibility.onThrottledClick {
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
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }
    }

    override fun loadData() {

    }
}