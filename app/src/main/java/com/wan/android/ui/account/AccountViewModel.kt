package com.wan.android.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tencent.mmkv.MMKV
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiModel
import com.wan.common.constant.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val mRepository: AccountRepository) : BaseViewModel() {

    private val mmkv by lazy { MMKV.defaultMMKV()!! }
    val isLogin: Boolean get() = _isLogged.value ?: false
    private val _isLogged = MutableLiveData(false)

    private val _uiState = MutableLiveData<BaseUiModel<Boolean>>()
    val uiState: LiveData<BaseUiModel<Boolean>>
        get() = _uiState

    init {
        _isLogged.value = mmkv.decodeBool(Const.IS_LOGIN, false)
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.logout() }
            if (result is Result.Success) {
                emitUiState(showSuccess = true)
            } else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: Boolean? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _uiState.value = uiModel
    }
}