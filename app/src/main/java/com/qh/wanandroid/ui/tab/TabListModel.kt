package com.qh.wanandroid.ui.tab

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/3.
 */
class TabListModel : BaseModel(), TabListContract.Model {

    override fun loadData(type: Int, id: Int, pageNum: Int): Observable<HttpResult<ArticleEntity>> {
        return if (type == Const.PROJECT_TYPE)
            HttpHelper.apiService.getProjectList(pageNum, id)
        else HttpHelper.apiService.getAccountList(id, pageNum)
    }

    override fun collect(id: Int): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.collect(id)
    }

    override fun unCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.unCollect(id)
    }
}