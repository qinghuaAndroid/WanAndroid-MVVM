package com.wan.android.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.SystemListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
@HiltViewModel
class SystemListViewModel @Inject constructor(private val mRepository: SystemListRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<SystemListUiState>()
    val uiState: LiveData<SystemListUiState>
        get() = _uiState

    fun getSystemList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getSystemList() }
            if (result is Result.Success) emitSystemListUiState(showSuccess = result.data)
            else if (result is Result.Error) emitSystemListUiState(showError = result.exception.message)
        }
    }

    private fun emitSystemListUiState(
        showError: String? = null,
        showSuccess: MutableList<SystemListEntity>? = null
    ) {
        val listUiState =
            SystemListUiState(showError, showSuccess)
        _uiState.value = listUiState
    }
}

data class SystemListUiState(
    val showError: String?,
    val showSuccess: MutableList<SystemListEntity>?
)