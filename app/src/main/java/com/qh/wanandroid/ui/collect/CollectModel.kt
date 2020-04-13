package com.qh.wanandroid.ui.collect

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.http.HttpHelper
import com.zs.wanandroid.entity.CollectEntity
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/13.
 */
class CollectModel: BaseModel(), CollectContract.Model {
    override fun loadData(page: Int): Observable<HttpResult<CollectEntity>> {
        return HttpHelper.apiService.getCollectData(page)
    }

    override fun cancelCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.unCollect(id)
    }
}