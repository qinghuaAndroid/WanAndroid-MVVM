package com.wan.android.ui.account

import android.content.Context
import com.wan.android.http.ApiService
import com.wan.baselib.di.getMMKV
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.common.constant.Const
import com.wan.login.bean.User
import com.wan.login.db.UserDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseRepository() {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun getUser(): Flow<User?> {
        return userDao.getUser()
    }

    suspend fun logout(): Result<Any> {
        return safeApiCall { requestLogout() }
    }

    private suspend fun requestLogout(): Result<Any> {
        return executeResponse(apiService.logout(), {
            getMMKV(context).apply {
                encode(Const.IS_LOGIN, false)
                userDao.deleteUser()
            }
        })
    }
}