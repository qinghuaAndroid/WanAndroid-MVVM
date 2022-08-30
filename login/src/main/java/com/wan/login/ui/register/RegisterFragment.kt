package com.wan.login.ui.register

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMFragment
import com.wan.login.R
import com.wan.login.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import splitties.views.onClick

/**
 * @author FQH
 * Create at 2020/6/23.
 */
@AndroidEntryPoint
class RegisterFragment : BaseVMFragment<RegisterViewModel, FragmentRegisterBinding>() {

    private val mViewModel by viewModels<RegisterViewModel>()
    private val args: RegisterFragmentArgs by navArgs()
    private var isPasswordShow = false
    private var isRePasswordShow = false

    override fun subscribeUi() {
        mViewModel.uiState.observe(viewLifecycleOwner) {
            if (it.showProgress) showProgressDialog()
            it.showSuccess?.let {
                dismissProgressDialog()
                view?.let { it1 -> Navigation.findNavController(it1).navigateUp() }
            }
            it.showError?.let { errorMsg ->
                showToast(errorMsg)
            }
        }
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_register

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.userName.set(args.account)
    }

    override fun initView(view: View) {
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
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
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }
        binding.ivRePasswordVisibility.onClick {
            binding.etRePassword.requestFocus()
            binding.etRePassword.transformationMethod = if (isRePasswordShow) {
                isRePasswordShow = false
                //显示密码状态
                binding.ivRePasswordVisibility.setImageResource(R.mipmap.password_show)
                PasswordTransformationMethod.getInstance()

            } else {
                isRePasswordShow = true
                //显示明文状态,手动将光标移到最后
                binding.ivRePasswordVisibility.setImageResource(R.mipmap.password_hide)
                HideReturnsTransformationMethod.getInstance()
            }
            binding.etRePassword.setSelection(binding.etRePassword.text?.length ?: 0)
        }
    }

    override fun loadData() {

    }
}