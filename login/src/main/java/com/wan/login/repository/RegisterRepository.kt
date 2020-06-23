package com.wan.login.repository

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.wan.login.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class RegisterRepository : BaseRepository() {

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