package com.wan.android.ui.account

import com.tencent.mmkv.MMKV
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.common.constant.Const
import com.wan.login.bean.User
import com.wan.login.db.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(private val apiService: ApiService, private val userDao: UserDao) : BaseRepository() {

    fun getUser(): Flow<User?> {
        return userDao.getUser()
    }

    suspend fun logout(): Result<Any> {
        return safeApiCall { requestLogout() }
    }

    private suspend fun requestLogout(): Result<Any> {
        return executeResponse(apiService.logout(), {
            MMKV.defaultMMKV().apply {
                encode(Const.IS_LOGIN, false)
                userDao.deleteUser()
            }
        })
    }
}