package com.wan.common.base

data class BaseUiState<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?
)

data class ListUiState<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)