package com.qh.wanandroid.ui.system.list

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.SystemListEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListRepository: BaseRepository() {

    suspend fun getSystemList():Result<MutableList<SystemListEntity>>{
        return safeApiCall({requestSystemList()}, "")
    }

    private suspend fun requestSystemList():Result<MutableList<SystemListEntity>> {
        return executeResponse(HttpHelper.apiService.getSystemList())
    }
}