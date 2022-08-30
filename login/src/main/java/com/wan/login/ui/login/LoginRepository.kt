package com.wan.login.ui.login

import android.content.Context
import com.wan.baselib.di.getMMKV
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.common.constant.Const
import com.wan.login.bean.User
import com.wan.login.db.UserDao
import com.wan.login.http.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/7.
 */
class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseRepository() {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    suspend fun login(username: String, password: String): Result<User> {
        return safeApiCall(call = { requestLogin(username, password) })
    }

    private suspend fun requestLogin(username: String, password: String): Result<User> {
        val response = apiService.login(username, password)
        return executeResponse(response, {
            getMMKV(context).run {
                encode(Const.IS_LOGIN, true)
                userDao.saveUser(response.data)
            }
        })
    }
}