package com.wan.login.repository

import com.example.common.constant.Const
import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.wan.login.http.HttpHelper
import com.tencent.mmkv.MMKV
import com.wan.login.bean.UserEntity

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class LoginRepository : BaseRepository() {

    suspend fun login(username: String, password: String): Result<UserEntity> {
        return safeApiCall(call = { requestLogin(username, password) })
    }

    private suspend fun requestLogin(username: String, password: String): Result<UserEntity> {
        val response = HttpHelper.apiService.login(username, password)
        return executeResponse(response, {
            val user = response.data
            MMKV.defaultMMKV().run {
                encode(Const.IS_LOGIN, true)
                encode(Const.USER_GSON, user)
            }
        })
    }
}