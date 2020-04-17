package com.qh.wanandroid.base

data class BaseUiModel<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?
)

data class ListUiModel<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)