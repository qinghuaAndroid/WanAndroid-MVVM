package com.wan.android.ui.account

import androidx.lifecycle.*
import com.tencent.mmkv.MMKV
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiState
import com.wan.common.constant.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val mRepository: AccountRepository
) : BaseViewModel() {

    private val mmkv by lazy(LazyThreadSafetyMode.NONE) { MMKV.defaultMMKV() }

    private val _isLogged = MediatorLiveData<Boolean>()
    val isLogged: LiveData<Boolean> get() = _isLogged

    val isLogin: Boolean
        get() = mmkv.decodeBool(Const.IS_LOGIN, false)

    private val _uiState = MutableLiveData<BaseUiState<Boolean>>()
    val uiState: LiveData<BaseUiState<Boolean>>
        get() = _uiState

    init {
        _isLogged.addSource(mRepository.getUser().asLiveData()) { user ->
            _isLogged.postValue(user != null)
        }
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
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _uiState.value = baseUiState
    }
}