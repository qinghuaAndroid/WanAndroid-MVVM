package com.qh.wanandroid.ui.integral

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.IntegralRecordEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class IntegralRepository: BaseRepository() {

    suspend fun getIntegralRecord(pageNum: Int):Result<IntegralRecordEntity>{
        return safeApiCall { requestIntegralRecord(pageNum) }
    }

    private suspend fun requestIntegralRecord(pageNum: Int):Result<IntegralRecordEntity> {
        return executeResponse(HttpHelper.apiService.getIntegralRecord(pageNum))
    }
}