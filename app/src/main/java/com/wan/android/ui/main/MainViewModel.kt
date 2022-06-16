package com.wan.android.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.UserInfoEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mRepository: MainRepository): BaseViewModel() {

    private val _uiState = MutableLiveData<BaseUiModel<UserInfoEntity>>()
    val uiState: LiveData<BaseUiModel<UserInfoEntity>>
        get() = _uiState


    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getUserInfo() }
            if (result is Result.Success) {
                emitUiState(showSuccess = result.data)
            } else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: UserInfoEntity? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _uiState.value = uiModel
    }
}