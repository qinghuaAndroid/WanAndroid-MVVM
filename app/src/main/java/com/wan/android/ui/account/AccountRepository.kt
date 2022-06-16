package com.wan.android.ui.account

import com.tencent.mmkv.MMKV
import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.wan.common.constant.Const
import javax.inject.Inject

class AccountRepository @Inject constructor() : BaseRepository() {

    suspend fun logout(): Result<Any> {
        return safeApiCall { requestLogout() }
    }

    private suspend fun requestLogout(): Result<Any> {
        return executeResponse(HttpHelper.apiService.logout(), {
            MMKV.defaultMMKV().apply {
                encode(Const.IS_LOGIN, false)
                removeValueForKey(Const.USER_GSON)
            }
        })
    }
}