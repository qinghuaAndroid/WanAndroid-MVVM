package com.qh.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.example.common.base.BaseUiModel
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.BannerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/16.
 */
class HomeViewModel(private val mRepository: HomeRepository) : BaseViewModel() {

    private val _topArticleUiState = MutableLiveData<BaseUiModel<MutableList<ArticleEntity.DatasBean>>>()
    val topArticleUiState: LiveData<BaseUiModel<MutableList<ArticleEntity.DatasBean>>>
        get() = _topArticleUiState
    private val _bannerUiState = MutableLiveData<BaseUiModel<MutableList<BannerEntity>>>()
    val bannerUiState: LiveData<BaseUiModel<MutableList<BannerEntity>>>
        get() = _bannerUiState

    fun getTopArticles() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.loadTopArticles() }
            if (result is Result.Success) emitTopArticleUiState(showSuccess = result.data)
            else if (result is Result.Error) emitTopArticleUiState(showError = result.exception.message)
        }
    }

    fun getBanner() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.loadBanner() }
            if (result is Result.Success) emitBannerUiState(showSuccess = result.data)
            else if (result is Result.Error) emitBannerUiState(showError = result.exception.message)
        }
    }

    private fun emitTopArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<ArticleEntity.DatasBean>? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _topArticleUiState.value = uiModel
    }

    private fun emitBannerUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<BannerEntity>? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _bannerUiState.value = uiModel
    }
}