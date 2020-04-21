package com.qh.wanandroid.ui.main

import com.example.devlibrary.mvp.BaseModel
import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.UserInfoEntity
import com.qh.wanandroid.http.HttpHelper
import io.reactivex.Observable

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