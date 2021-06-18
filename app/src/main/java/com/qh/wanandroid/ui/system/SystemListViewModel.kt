package com.qh.wanandroid.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.qh.wanandroid.bean.SystemListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListViewModel(private val mRepository: SystemListRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<SystemListUiModel>()
    val uiState: LiveData<SystemListUiModel>
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
        val uiModel =
            SystemListUiModel(showError, showSuccess)
        _uiState.value = uiModel
    }
}

data class SystemListUiModel(
    val showError: String?,
    val showSuccess: MutableList<SystemListEntity>?
)