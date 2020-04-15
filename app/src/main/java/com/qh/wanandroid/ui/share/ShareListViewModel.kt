package com.qh.wanandroid.ui.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class ShareListViewModel(private val mRepository: ShareListRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<ShareListUiModel>()
    val uiState: LiveData<ShareListUiModel>
        get() = _uiState

    private var pageNum = 0

    fun getMyArticle(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiModel(showLoading = true)
            if (isRefresh) {
                pageNum = 0
                emitUiModel(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getShareArticle(pageNum) }
            if (result is Result.Success) {
                val articleEntity = result.data
                emitUiModel(
                    showLoading = false,
                    showSuccess = articleEntity,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                articleEntity.run {
                    if (curPage >= pageCount) {
                        emitUiModel(showEnd = true)
                        return@launch
                    }
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

    private fun emitUiModel(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: ArticleEntity? = null,
        showEnd: Boolean = false, // 加载更多
        isRefresh: Boolean = false, // 刷新
        isEnableLoadMore: Boolean = false
    ) {
        val uiModel = ShareListUiModel(
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

data class ShareListUiModel(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: ArticleEntity?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)