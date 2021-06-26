package com.wan.android.ui.integral

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.IntegralRecordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class IntegralViewModel(private val mRepository: IntegralRepository) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<IntegralUiModel>()
    val uiState: LiveData<IntegralUiModel>
        get() = _uiState

    private var pageNum = 1

    fun getIntegralRecord(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(showLoading = true)
            if (isRefresh) {
                pageNum = 1
                emitArticleUiState(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getIntegralRecord(pageNum) }
            if (result is Result.Success) {
                val data = result.data
                emitArticleUiState(
                    showLoading = false,
                    showSuccess = data,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                if (data.curPage >= data.pageCount) {
                    emitArticleUiState(showLoading = false, showEnd = true)
                    return@launch
                }
                pageNum++
            } else if (result is Result.Error) {
                emitArticleUiState(
                    showLoading = false,
                    showError = result.exception.message,
                    isEnableLoadMore = true
                )
            }
        }
    }

    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: IntegralRecordEntity? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        isEnableLoadMore: Boolean = false
    ) {
        val uiModel =
            IntegralUiModel(
                showLoading,
                showError,
                showSuccess,
                showEnd,
                isRefresh,
                isEnableLoadMore
            )
        _uiState.value = uiModel
    }

}

data class IntegralUiModel(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: IntegralRecordEntity?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)