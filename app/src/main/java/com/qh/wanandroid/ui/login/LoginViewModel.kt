package com.qh.wanandroid.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class LoginViewModel(private val mRepository: LoginRepository) : BaseViewModel() {
    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiModel>()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    val verifyInput: (String) -> Unit = { loginDataChanged() }

    private fun isInputValid(userName: String, passWord: String) =
        userName.isNotBlank() && passWord.isNotBlank()

    fun loginDataChanged() {
        emitUiState(enableLoginButton = isInputValid(userName.get() ?: "", passWord.get() ?: ""))
    }

    fun login() {
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank()) {
                emitUiState(enableLoginButton = false)
                return@launch
            }
            withContext(Dispatchers.Main) { showLoading() }

            val result = mRepository.login(userName.get() ?: "", passWord.get() ?: "")

            withContext(Dispatchers.Main) {
                checkResult(result, {
                    emitUiState(showSuccess = it, enableLoginButton = true)
                }, {
                    emitUiState(showError = it, enableLoginButton = true)
                })
            }
        }
    }

    private inline fun <T : Any> checkResult(
        result: Result<T>,
        success: (T) -> Unit,
        error: (String?) -> Unit
    ) {
        if (result is Result.Success) {
            success(result.data)
        } else if (result is Result.Error) {
            error(result.exception.message)
        }
    }

    private fun showLoading() {
        emitUiState(true)
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        showError: String? = null,
        showSuccess: UserEntity? = null,
        enableLoginButton: Boolean = false,
        needLogin: Boolean = false
    ) {
        val uiModel =
            LoginUiModel(showProgress, showError, showSuccess, enableLoginButton, needLogin)
        _uiState.value = uiModel
    }
}

data class LoginUiModel(
    val showProgress: Boolean,
    val showError: String?,
    val showSuccess: UserEntity?,
    val enableLoginButton: Boolean,
    val needLogin: Boolean
)
