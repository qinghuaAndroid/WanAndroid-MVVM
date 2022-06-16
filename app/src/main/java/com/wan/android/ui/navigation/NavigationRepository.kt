package com.wan.android.ui.navigation

import com.wan.android.bean.NavigationEntity
import com.wan.android.http.HttpHelper
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/8.
 */
class NavigationRepository @Inject constructor() : BaseRepository() {

    suspend fun getNavigation(): Result<MutableList<NavigationEntity>> {
        return safeApiCall { requestNavigation() }
    }

    private suspend fun requestNavigation(): Result<MutableList<NavigationEntity>> {
        return executeResponse(HttpHelper.apiService.getNavigation())
    }
}