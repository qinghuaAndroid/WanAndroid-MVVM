package com.wan.android.ui.main

import com.wan.baselib.mvp.BaseModel
import com.wan.baselib.network.HttpResult
import com.wan.android.bean.UserInfoEntity
import com.wan.android.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class MainModel: BaseModel(),MainContract.Model {
    override fun logout(): Observable<HttpResult<Any>> {
        return HttpHelper.apiService.logout()
    }

    override fun getUserInfo(): Observable<HttpResult<UserInfoEntity>> {
        return HttpHelper.apiService.getUserInfo()
    }
}