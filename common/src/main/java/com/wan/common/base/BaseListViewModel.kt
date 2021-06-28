package com.wan.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wan.baselib.mvvm.BaseViewModel

/**
 * @author cy
 * Create at 2020/4/16.
 */
open class BaseListViewModel<T> : BaseViewModel() {

    private val _uiState = MutableLiveData<ListUiModel<T>>()
    val uiState: LiveData<ListUiModel<T>>
        get() = _uiState

    fun emitUiModel(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: T? = null,
        showEnd: Boolean = false, // 加载更多
        isRefresh: Boolean = false, // 刷新
        isEnableLoadMore: Boolean = false
    ) {
        val uiModel = ListUiModel(
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