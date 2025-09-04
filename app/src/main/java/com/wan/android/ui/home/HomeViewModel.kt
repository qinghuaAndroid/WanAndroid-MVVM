package com.wan.android.ui.home

import androidx.lifecycle.viewModelScope
import com.wan.android.bean.ArticleEntity
import com.wan.android.bean.BannerEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/16.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val mRepository: HomeRepository) : BaseViewModel() {

    private val _topArticleUiState =
        MutableSharedFlow<BaseUiState<MutableList<ArticleEntity.DatasBean>>>()
    val topArticleUiState: SharedFlow<BaseUiState<MutableList<ArticleEntity.DatasBean>>>
        get() = _topArticleUiState
    private val _bannerUiState = MutableSharedFlow<BaseUiState<MutableList<BannerEntity>>>()
    val bannerUiState: SharedFlow<BaseUiState<MutableList<BannerEntity>>>
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

    private suspend fun emitTopArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<ArticleEntity.DatasBean>? = null
    ) {
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _topArticleUiState.emit(baseUiState)
    }

    private suspend fun emitBannerUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<BannerEntity>? = null
    ) {
        val baseUiState = BaseUiState(showLoading, showError, showSuccess)
        _bannerUiState.emit(baseUiState)
    }
}