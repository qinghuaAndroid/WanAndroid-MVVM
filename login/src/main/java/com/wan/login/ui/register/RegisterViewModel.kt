package com.wan.login.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.login.ui.register.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/6/23.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val mRepository: RegisterRepository) :
    BaseViewModel() {

    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")
    val rePassWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<RegisterUiState>()
    val uiState: LiveData<RegisterUiState>
        get() = _uiState

    val verifyInput: (String) -> Unit = { registerDataChanged() }

    private fun isInputValid(userName: String, passWord: String, rePassWord: String) =
        userName.isNotBlank() && passWord.isNotBlank() && rePassWord.isNotBlank()

    private fun registerDataChanged() {
        emitUiState(
            enableRegisterButton = isInputValid(
                userName.get() ?: "",
                passWord.get() ?: "",
                rePassWord.get() ?: ""
            )
        )
    }

    fun register() {
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.get().isNullOrBlank() || passWord.get().isNullOrBlank() || rePassWord.get()
                    .isNullOrBlank()
            ) {
                emitUiState(enableRegisterButton = false)
                return@launch
            }
            withContext(Dispatchers.Main) { showLoading() }

            val result = mRepository.register(
                userName.get() ?: "",
                passWord.get() ?: "",
                rePassWord.get() ?: ""
            )

            withContext(Dispatchers.Main) {
                checkResult(result, {
                    emitUiState(showSuccess = it, enableRegisterButton = true)
                }, {
                    emitUiState(showError = it, enableRegisterButton = true)
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
        showSuccess: Any? = null,
        enableRegisterButton: Boolean = false
    ) {
        val registerUiState =
            RegisterUiState(showProgress, showError, showSuccess, enableRegisterButton)
        _uiState.value = registerUiState
    }
}

data class RegisterUiState(
    val showProgress: Boolean,
    val showError: String?,
    val showSuccess: Any?,
    val enableRegisterButton: Boolean
)