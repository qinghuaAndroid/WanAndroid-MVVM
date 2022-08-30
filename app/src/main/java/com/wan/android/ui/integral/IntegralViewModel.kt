package com.wan.android.ui.integral

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.IntegralRecordEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/10.
 */
@HiltViewModel
class IntegralViewModel @Inject constructor(private val mRepository: IntegralRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<ListUiState<IntegralRecordEntity>>()
    val uiState: LiveData<ListUiState<IntegralRecordEntity>>
        get() = _uiState

    private var pageNum = 1

    fun getIntegralRecord(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiState(showLoading = true)
            if (isRefresh) {
                pageNum = 1
                emitUiState(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getIntegralRecord(pageNum) }
            if (result is Result.Success) {
                val data = result.data
                emitUiState(
                    showLoading = false,
                    showSuccess = data,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                if (data.curPage >= data.pageCount) {
                    emitUiState(showLoading = false, showEnd = true)
                    return@launch
                }
                pageNum++
            } else if (result is Result.Error) {
                emitUiState(
                    showLoading = false,
                    showError = result.exception.message,
                    isEnableLoadMore = true
                )
            }
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: IntegralRecordEntity? = null,
        showEnd: Boolean = false, // 加载更多
        isRefresh: Boolean = false, // 刷新
        isEnableLoadMore: Boolean = false
    ) {
        val listUiState = ListUiState(
            showLoading,
            showError,
            showSuccess,
            showEnd,
            isRefresh,
            isEnableLoadMore
        )
        _uiState.value = listUiState
    }
}