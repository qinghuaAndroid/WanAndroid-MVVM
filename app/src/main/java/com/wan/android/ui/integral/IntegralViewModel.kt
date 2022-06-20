package com.wan.android.ui.integral

import androidx.lifecycle.viewModelScope
import com.wan.android.bean.IntegralRecordEntity
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseListViewModel
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
class IntegralViewModel @Inject constructor(private val mRepository: IntegralRepository) :
    BaseListViewModel<IntegralRecordEntity>() {

    private var pageNum = 1

    fun getIntegralRecord(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiModel(showLoading = true)
            if (isRefresh) {
                pageNum = 1
                emitUiModel(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getIntegralRecord(pageNum) }
            if (result is Result.Success) {
                val data = result.data
                emitUiModel(
                    showLoading = false,
                    showSuccess = data,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                if (data.curPage >= data.pageCount) {
                    emitUiModel(showLoading = false, showEnd = true)
                    return@launch
                }
                pageNum++
            } else if (result is Result.Error) {
                emitUiModel(
                    showLoading = false,
                    showError = result.exception.message,
                    isEnableLoadMore = true
                )
            }
        }
    }
}