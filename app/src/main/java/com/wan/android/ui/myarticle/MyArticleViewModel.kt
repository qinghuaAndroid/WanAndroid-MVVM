package com.wan.android.ui.myarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.MyArticleEntity
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
 * Create at 2020/4/15.
 */
@HiltViewModel
class MyArticleViewModel @Inject constructor(private val mRepository: MyArticleRepository) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<ListUiState<MyArticleEntity>>()
    val uiState: LiveData<ListUiState<MyArticleEntity>>
        get() = _uiState

    private var pageNum = 0

    fun getMyArticle(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiState(showLoading = true)
            if (isRefresh) {
                pageNum = 0
                emitUiState(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) { mRepository.getMyArticle(pageNum) }
            if (result is Result.Success) {
                val myArticleEntity = result.data
                emitUiState(
                    showLoading = false,
                    showSuccess = myArticleEntity,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                val shareArticles = myArticleEntity.shareArticles
                shareArticles?.let {
                    if (it.curPage >= it.pageCount) {
                        emitUiState(showEnd = true)
                        return@launch
                    }
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
        showSuccess: MyArticleEntity? = null,
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