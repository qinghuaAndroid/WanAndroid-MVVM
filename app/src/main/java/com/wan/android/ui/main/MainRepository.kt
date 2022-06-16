package com.wan.android.ui.main

import com.wan.android.bean.UserInfoEntity
import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject


class MainRepository @Inject constructor(): BaseRepository() {

    suspend fun getUserInfo(): Result<UserInfoEntity> {
        return safeApiCall { requestUserInfo() }
    }

    private suspend fun requestUserInfo(): Result<UserInfoEntity> {
        return executeResponse(HttpHelper.apiService.getUserInfo())
    }
}