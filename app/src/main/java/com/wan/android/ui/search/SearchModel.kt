package com.wan.android.ui.search

import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.wan.android.bean.HotSearchEntity
import com.wan.android.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

class SearchModel : BaseModel(), SearchContract.Model {

    override fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>> {
        return HttpHelper.apiService.getHotSearchData()
    }

}