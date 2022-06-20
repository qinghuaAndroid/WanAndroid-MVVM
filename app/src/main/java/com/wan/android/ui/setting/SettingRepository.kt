package com.wan.android.ui.setting

import android.content.Context
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.utils.CacheUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context) :
    BaseRepository() {

    fun getCacheSize(): String {
        return CacheUtils.getTotalCacheSize(context)
    }

    fun clearCache() {
        CacheUtils.clearAllCache(context)
    }
}