package com.wan.android.ui.girl

import com.wan.baselib.mvp.BaseModel
import com.wan.android.bean.GankIoDataBean
import com.wan.android.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

class GirlModel : BaseModel(), GirlContract.Model {
    override fun requestMeiziList(category: String, type: String, count: Int, page: Int): Observable<GankIoDataBean> {
        return HttpHelper.gankService.getGankIoData(category,type, count, page)
    }
}