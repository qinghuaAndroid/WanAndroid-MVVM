package com.wan.android.ui.setting

import com.wan.baselib.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val mRepository: SettingRepository
) : BaseViewModel() {

    private val _cacheValue = MutableStateFlow("")
    val cacheValue: StateFlow<String> get() = _cacheValue

    fun getCacheSize() {
        _cacheValue.value = mRepository.getCacheSize()
    }

    fun clearCache() {
        mRepository.clearCache()
        _cacheValue.value = String.format("%s", "0M")
    }
}