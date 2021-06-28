package com.wan.android.ui.collect

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.android.bean.ArticleEntity
import com.wan.android.http.HttpHelper

/**
 * @author cy
 * Create at 2020/4/16.
 */
class CollectRepository: BaseRepository() {

    suspend fun getCollectData(pageNum: Int):Result<ArticleEntity>{
        return safeApiCall { requestCollectData(pageNum) }
    }

    private suspend fun requestCollectData(pageNum: Int):Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getCollectData(pageNum))
    }

    suspend fun collect(id: Int):Result<Any>{
        return safeApiCall { requestCollect(id) }
    }

    private suspend fun requestCollect(id: Int):Result<Any> {
        return executeResponse(HttpHelper.apiService.collect(id))
    }

    suspend fun unCollect(id: Int):Result<Any>{
        return safeApiCall { requestCancelCollect(id) }
    }

    private suspend fun requestCancelCollect(id: Int):Result<Any> {
        return executeResponse(HttpHelper.apiService.unCollect(id))
    }


}