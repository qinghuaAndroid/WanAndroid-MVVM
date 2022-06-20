package com.wan.login.repository

import com.tencent.mmkv.MMKV
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.common.constant.Const
import com.wan.login.bean.User
import com.wan.login.db.UserDao
import com.wan.login.http.ApiService
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/7.
 */
class LoginRepository @Inject constructor(private val apiService: ApiService, private val userDao: UserDao) : BaseRepository() {

    suspend fun login(username: String, password: String): Result<User> {
        return safeApiCall(call = { requestLogin(username, password) })
    }

    private suspend fun requestLogin(username: String, password: String): Result<User> {
        val response = apiService.login(username, password)
        return executeResponse(response, {
            MMKV.defaultMMKV()!!.run {
                encode(Const.IS_LOGIN, true)
                userDao.saveUser(response.data)
            }
        })
    }
}