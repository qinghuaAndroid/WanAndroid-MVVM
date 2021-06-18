package com.wan.login.http

import com.wan.common.constant.Const
import com.wan.baselib.network.HttpsUtils

/**
 * Description: 获取各种类型ApiService
 * Created by FQH on 2019/10/11.
 */
object HttpHelper {
    val apiService : ApiService by lazy { HttpsUtils.getRetrofit(Const.BASE_URL).create(ApiService::class.java) }
}
