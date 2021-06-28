package com.wan.android.ui.setting

import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.wan.android.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

/**
 * @author cy
 * Create at 2020/4/10.
 */
class SettingModel: BaseModel(),SettingContract.Model {
    override fun logout(): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.logout()
    }
}