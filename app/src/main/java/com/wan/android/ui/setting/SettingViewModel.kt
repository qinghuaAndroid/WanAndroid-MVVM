package com.wan.android.ui.setting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.utils.CacheUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val mRepository: SettingRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    private val _cacheValue = MutableLiveData<String>()
    val cacheValue: LiveData<String> get() = _cacheValue

    fun getCacheSize() {
        _cacheValue.value = CacheUtils.getTotalCacheSize(context)
    }

    fun clearCache() {
        CacheUtils.clearAllCache(context)
        _cacheValue.value = String.format("%s", "0M")
    }
}