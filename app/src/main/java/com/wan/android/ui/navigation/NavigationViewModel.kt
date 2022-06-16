package com.wan.android.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.NavigationEntity
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(private val mRepository: NavigationRepository) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<NavigationUiModel>()
    val uiState: LiveData<NavigationUiModel>
        get() = _uiState

    fun getNavigation() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getNavigation() }
            if (result is Result.Success) emitNavigationUiState(showSuccess = result.data)
            else if (result is Result.Error) emitNavigationUiState(showError = result.exception.message)
        }
    }

    private fun emitNavigationUiState(
        showError: String? = null,
        showSuccess: MutableList<NavigationEntity>? = null
    ) {
        val uiModel = NavigationUiModel(showError, showSuccess)
        _uiState.value = uiModel
    }
}

data class NavigationUiModel(
    val showError: String?,
    val showSuccess: MutableList<NavigationEntity>?
)