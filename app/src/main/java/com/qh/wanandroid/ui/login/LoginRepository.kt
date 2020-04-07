package com.qh.wanandroid.ui.login

import com.example.common.constant.Const
import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.example.devlibrary.utils.Preference
import com.google.gson.Gson
import com.qh.wanandroid.bean.UserEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class LoginRepository : BaseRepository() {

    private var isLogin by Preference(Const.IS_LOGIN, false)
    private var userJson by Preference(Const.USER_GSON, "")

    suspend fun login(username: String, password: String): Result<UserEntity> {
        return safeApiCall(call = { requestLogin(username, password) }, errorMessage = "登录失败!")
    }

    private suspend fun requestLogin(username: String, password: String): Result<UserEntity> {
        val response = HttpHelper.apiService.login(username, password)
        return executeResponse(response, {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
        })
    }
}