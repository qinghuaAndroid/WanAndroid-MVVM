package com.wan.android.ui.integral

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.IntegralRecordEntity
import com.wan.android.http.HttpHelper

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