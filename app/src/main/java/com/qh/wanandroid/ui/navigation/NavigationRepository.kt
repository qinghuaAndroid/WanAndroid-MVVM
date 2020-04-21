package com.qh.wanandroid.ui.navigation

import com.example.devlibrary.mvvm.BaseRepository
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.NavigationEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class NavigationRepository: BaseRepository() {

    suspend fun getNavigation():Result<MutableList<NavigationEntity>>{
        return safeApiCall {requestNavigation()}
    }

    private suspend fun requestNavigation():Result<MutableList<NavigationEntity>> {
        return executeResponse(HttpHelper.apiService.getNavigation())
    }
}