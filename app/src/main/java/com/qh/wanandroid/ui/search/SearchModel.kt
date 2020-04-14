package com.qh.wanandroid.ui.search

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.HotSearchEntity
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

class SearchModel : BaseModel(), SearchContract.Model {

    override fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>> {
        return HttpHelper.apiService.getHotSearchData()
    }

}