package com.wan.android.ui.system

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.SystemListEntity
import com.wan.android.http.HttpHelper

/**
 * @author cy
 * Create at 2020/4/8.
 */
class SystemListRepository: BaseRepository() {

    suspend fun getSystemList():Result<MutableList<SystemListEntity>>{
        return safeApiCall {requestSystemList()}
    }

    private suspend fun requestSystemList():Result<MutableList<SystemListEntity>> {
        return executeResponse(HttpHelper.apiService.getSystemList())
    }
}