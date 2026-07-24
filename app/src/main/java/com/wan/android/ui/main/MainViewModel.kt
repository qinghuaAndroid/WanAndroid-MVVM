package com.wan.android.ui.main

import androidx.lifecycle.viewModelScope
import com.wan.android.bean.UserInfoEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mRepository: MainRepository) : BaseViewModel() {

    private val _uiState = MutableSharedFlow<BaseUiState<UserInfoEntity>>()
    val uiState: SharedFlow<BaseUiState<UserInfoEntity>>
        get() = _uiState


    fun getUserInfo() {
        viewModelScope.launch {
            val result = mRepository.getUserInfo()
            if (result is Result.Success) {
                emitUiState(showSuccess = result.data)
            } else if (result is Result.Error) {
                emitUiState(showError = result.exception.message)
            }
        }
    }

    private suspend fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: UserInfoEntity? = null
    ) {
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _uiState.emit(baseUiState)
    }
}