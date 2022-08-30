package com.wan.android.ui.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.TabEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/3.
 */
@HiltViewModel
class TabViewModel @Inject constructor(private val mRepository: TabRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<BaseUiState<MutableList<TabEntity>>>()
    val uiState: LiveData<BaseUiState<MutableList<TabEntity>>>
        get() = _uiState

    fun getTabList(type: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getTabList(type) }
            if (result is Result.Success) emitUiState(showSuccess = result.data)
            else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<TabEntity>? = null
    ) {
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _uiState.value = baseUiState
    }
}