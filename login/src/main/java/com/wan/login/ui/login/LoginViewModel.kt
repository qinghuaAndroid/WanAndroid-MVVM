package com.wan.login.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.login.bean.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/7.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val mRepository: LoginRepository) :
    BaseViewModel() {

    val userName = ObservableField("")
    val passWord = ObservableField("")

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState>
        get() = _uiState

    private val _navigateToRegister = MutableSharedFlow<Boolean>()

    val navigateToRegister: SharedFlow<Boolean>
        get() = _navigateToRegister

    fun userClicksOnButton() {
        viewModelScope.launch { _navigateToRegister.emit(true) }
    }

    val verifyInput: (String) -> Unit = { loginDataChanged() }

    private fun isInputValid(userName: String, passWord: String) =
        userName.isNotBlank() && passWord.isNotBlank()

    private fun loginDataChanged() {
        emitUiState(enableLoginButton = isInputValid(userName.get() ?: "", passWord.get() ?: ""))
    }

    fun login() {
        viewModelScope.launch {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank()) {
                emitUiState(enableLoginButton = false)
                return@launch
            }
            emitUiState(true)

            val result = withContext(Dispatchers.Main) {
                mRepository.login(
                    userName.get() ?: "",
                    passWord.get() ?: ""
                )
            }

            if (result is Result.Success) {
                emitUiState(showSuccess = result.data, enableLoginButton = true)
            } else if (result is Result.Error) {
                emitUiState(showError = result.exception.message, enableLoginButton = true)
            }
        }
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        showError: String? = null,
        showSuccess: User? = null,
        enableLoginButton: Boolean = false,
        needLogin: Boolean = false
    ) {
        val loginUiState =
            LoginUiState(showProgress, showError, showSuccess, enableLoginButton, needLogin)
        _uiState.value = loginUiState
    }
}

data class LoginUiState(
    val showProgress: Boolean,
    val showError: String?,
    val showSuccess: User?,
    val enableLoginButton: Boolean,
    val needLogin: Boolean
)
