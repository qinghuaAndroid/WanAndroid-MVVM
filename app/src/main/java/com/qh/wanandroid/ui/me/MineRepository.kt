package com.qh.wanandroid.ui.me

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.IntegralEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class MineRepository : BaseRepository() {

    suspend fun getIntegral(): Result<IntegralEntity> {
        return safeApiCall(
            call = { requestIntegral() },
            errorMessage = ""
        )
    }

    private suspend fun requestIntegral(): Result<IntegralEntity> {
        return executeResponse(HttpHelper.apiService.getIntegral())
    }
}