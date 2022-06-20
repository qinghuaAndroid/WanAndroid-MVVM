package com.wan.android.ui.system

import com.wan.android.bean.SystemListEntity
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class SystemListRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    suspend fun getSystemList(): Result<MutableList<SystemListEntity>> {
        return safeApiCall { requestSystemList() }
    }

    private suspend fun requestSystemList(): Result<MutableList<SystemListEntity>> {
        return executeResponse(apiService.getSystemList())
    }
}