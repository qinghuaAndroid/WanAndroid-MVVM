package com.wan.android.ui.tab

import com.wan.android.bean.TabEntity
import com.wan.android.constant.Const
import com.wan.android.http.ApiService
import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import javax.inject.Inject


class TabRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun getTabList(type: Int): Result<MutableList<TabEntity>> {
        return safeApiCall { requestTabList(type) }
    }

    private suspend fun requestTabList(type: Int): Result<MutableList<TabEntity>> {
        val result = if (type == Const.PROJECT_TYPE) {
            apiService.getProjectTabList()
        } else {
            apiService.getAccountTabList()
        }
        return executeResponse(result)
    }
}