package com.wan.login.repository

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.login.http.HttpHelper
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/7.
 */
class RegisterRepository @Inject constructor() : BaseRepository() {

    suspend fun register(username: String, password: String, repassword: String): Result<Any> {
        return safeApiCall(call = { requestRegister(username, password, repassword) })
    }

    private suspend fun requestRegister(
        username: String,
        password: String,
        repassword: String
    ): Result<Any> {
        return executeResponse(HttpHelper.apiService.register(username, password, repassword))
    }
}