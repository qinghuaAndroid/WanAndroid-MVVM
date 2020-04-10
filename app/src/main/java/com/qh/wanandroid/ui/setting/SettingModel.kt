package com.qh.wanandroid.ui.setting

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class SettingModel: BaseModel(),SettingContract.Model {
    override fun logout(): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.logout()
    }
}