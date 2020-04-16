package com.qh.wanandroid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.devlibrary.mvvm.BaseViewModel

/**
 * @author FQH
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

data class ListUiModel<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)