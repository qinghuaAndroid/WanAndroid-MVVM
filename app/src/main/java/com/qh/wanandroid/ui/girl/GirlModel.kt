package com.qh.wanandroid.ui.girl

import com.example.devlibrary.mvp.BaseModel
import com.qh.wanandroid.bean.GankIoDataBean
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

class GirlModel : BaseModel(), GirlContract.Model {
    override fun requestMeiziList(category: String, type: String, count: Int, page: Int): Observable<GankIoDataBean> {
        return HttpHelper.gankService.getGankIoData(category,type, count, page)
    }
}