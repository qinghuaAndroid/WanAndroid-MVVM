package com.wan.login.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/6/23.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val mRepository: RegisterRepository) :
    BaseViewModel() {

    val userName = ObservableField("")
    val passWord = ObservableField("")
    val rePassWord = ObservableField("")

    private val _uiState = MutableLiveData<RegisterUiState>()
    val uiState: LiveData<RegisterUiState>
        get() = _uiState

    val verifyInput: (String) -> Unit = { registerDataChanged() }

    private val isInputValid
        get() = listOf(userName, passWord, rePassWord).all {
            !it.get().isNullOrBlank()
        }

    private fun registerDataChanged() {
        emitUiState(enableRegisterButton = isInputValid)
    }

    fun register() {
        viewModelScope.launch {
            if (!isInputValid) {
                emitUiState(enableRegisterButton = false)
                return@launch
            }
            showLoading()

            val result = mRepository.register(
                userName.get() ?: "",
                passWord.get() ?: "",
                rePassWord.get() ?: ""
            )

            checkResult(result, {
                emitUiState(showSuccess = it, enableRegisterButton = true)
            }, {
                emitUiState(showError = it, enableRegisterButton = true)
            })
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