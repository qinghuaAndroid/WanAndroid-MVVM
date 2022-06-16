package com.wan.android.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.HotSearchEntity
import com.wan.android.bean.SearchHistoryBean
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val mRepository: SearchRepository) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<BaseUiModel<MutableList<HotSearchEntity>>>()
    val uiState: LiveData<BaseUiModel<MutableList<HotSearchEntity>>>
        get() = _uiState

    private val _resultsChange = MutableLiveData<ResultsChange<SearchHistoryBean>>()
    val resultsChange: LiveData<ResultsChange<SearchHistoryBean>>
        get() = _resultsChange

    fun queryAll() {
        viewModelScope.launch {
            mRepository.queryAll()
                .collect { resultsChange: ResultsChange<SearchHistoryBean> ->
                    _resultsChange.value = resultsChange
                    when (resultsChange) {
                        is InitialResults -> println("Initial results size: ${resultsChange.list.size}")
                        is UpdatedResults ->
                            println("Updated results size: ${resultsChange.list.size} insertions ${resultsChange.insertions.size}")
                    }
                }
        }
    }

    fun saveSearchKey(key: String) {
        viewModelScope.launch {
            mRepository.saveSearchKey(key)
        }
    }

    fun deleteByKey(key: String) {
        mRepository.deleteByKey(key)
    }

    fun clearAll() {
        mRepository.clearAll()
    }

    fun closeRealm() {
        mRepository.closeRealm()
    }

    fun getHotSearchData() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getHotSearchData() }
            if (result is Result.Success) emitUiState(showSuccess = result.data)
            else if (result is Result.Error) emitUiState(showError = result.exception.message)
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: MutableList<HotSearchEntity>? = null
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _uiState.value = uiModel
    }
}