package com.wan.android.ui.collect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/16.
 */
class CollectViewModel(private val mRepository: CollectRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<BaseUiModel<Boolean>>()
    val uiState: LiveData<BaseUiModel<Boolean>>
        get() = _uiState

    fun collect(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.collect(id) }
            if (result is Result.Success) emitUiState(showSuccess = true)
            else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    fun unCollect(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.unCollect(id) }
            if (result is Result.Success) emitUiState(showSuccess = false)
            else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: Boolean? = null //true表示收藏成功，false表示取消收藏成功
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _uiState.value = uiModel
    }
}