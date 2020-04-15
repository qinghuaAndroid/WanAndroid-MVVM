package com.qh.wanandroid.ui.search.list

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
 * Create at 2020/4/8.
 */
class SearchListViewModel(private val mRepository: SearchListRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<ArticleUiModel>()
    val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    private var pageNum = 0

    fun getSystemArticle(isRefresh: Boolean, key: String) {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(showLoading = true)
            if (isRefresh) {
                pageNum = 0
                emitArticleUiState(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.queryBySearchKey(pageNum, key) }
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
        showSuccess: ArticleEntity? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        isEnableLoadMore: Boolean = false
    ) {
        val uiModel =
            ArticleUiModel(
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

data class ArticleUiModel(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: ArticleEntity?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)