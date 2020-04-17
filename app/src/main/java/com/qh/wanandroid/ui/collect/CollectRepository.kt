package com.qh.wanandroid.ui.collect

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
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