package com.wan.android.ui.system

import androidx.lifecycle.viewModelScope
import com.wan.android.bean.SystemListEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
@HiltViewModel
class SystemListViewModel @Inject constructor(private val mRepository: SystemListRepository) :
    BaseViewModel() {

    private val _uiState = MutableSharedFlow<BaseUiState<MutableList<SystemListEntity>>>()
    val uiState: SharedFlow<BaseUiState<MutableList<SystemListEntity>>>
        get() = _uiState

    fun getSystemList() {
        viewModelScope.launch {
            val result = mRepository.getSystemList()
            if (result is Result.Success) emitSystemListUiState(showSuccess = result.data)
            else if (result is Result.Error) emitSystemListUiState(showError = result.exception.message)
        }
    }

    private suspend fun emitSystemListUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<SystemListEntity>? = null
    ) {
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _uiState.emit(baseUiState)
    }
}