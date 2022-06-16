package com.wan.android.ui.setting

import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

class SettingRepository @Inject constructor(): BaseRepository() {

    suspend fun logout(): Result<Any> {
        return safeApiCall { requestLogout() }
    }

    private suspend fun requestLogout(): Result<Any> {
        return executeResponse(HttpHelper.apiService.logout())
    }
}