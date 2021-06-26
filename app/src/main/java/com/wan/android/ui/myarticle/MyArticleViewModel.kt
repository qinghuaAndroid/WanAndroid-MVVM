package com.wan.android.ui.myarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.MyArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class MyArticleViewModel(private val mRepository: MyArticleRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<MyArticleUiModel>()
    val uiState: LiveData<MyArticleUiModel>
        get() = _uiState

    private var pageNum = 0

    fun getMyArticle(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiModel(showLoading = true)
            if (isRefresh) {
                pageNum = 0
                emitUiModel(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getMyArticle(pageNum) }
            if (result is Result.Success) {
                val myArticleEntity = result.data
                emitUiModel(
                    showLoading = false,
                    showSuccess = myArticleEntity,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                val shareArticles = myArticleEntity.shareArticles
                shareArticles?.let {
                    if (it.curPage >= it.pageCount) {
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
        showSuccess: MyArticleEntity? = null,
        showEnd: Boolean = false, // 加载更多
        isRefresh: Boolean = false, // 刷新
        isEnableLoadMore: Boolean = false
    ) {
        val uiModel = MyArticleUiModel(
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

data class MyArticleUiModel(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: MyArticleEntity?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
    val isEnableLoadMore: Boolean
)