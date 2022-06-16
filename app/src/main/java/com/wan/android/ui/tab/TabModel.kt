package com.wan.android.ui.tab

import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.wan.android.bean.TabEntity
import com.wan.android.constant.Const
import com.wan.android.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

/**
 * @author cy
 * Create at 2020/4/3.
 */
class TabModel : BaseModel(), TabContract.Model {
    override fun loadData(type: Int): Observable<HttpResult<MutableList<TabEntity>>> {
        return if (type == Const.PROJECT_TYPE) HttpHelper.apiService.getProjectTabList()
        else HttpHelper.apiService.getAccountTabList()
    }
}