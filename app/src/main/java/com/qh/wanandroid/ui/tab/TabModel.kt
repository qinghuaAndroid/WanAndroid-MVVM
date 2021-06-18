package com.qh.wanandroid.ui.tab

import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.qh.wanandroid.bean.TabEntity
import com.qh.wanandroid.constant.Const
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

/**
 * @author FQH
 * Create at 2020/4/3.
 */
class TabModel : BaseModel(), TabContract.Model {
    override fun loadData(type: Int): Observable<HttpResult<MutableList<TabEntity>>> {
        return if (type == Const.PROJECT_TYPE) HttpHelper.apiService.getProjectTabList()
        else HttpHelper.apiService.getAccountTabList()
    }
}