package com.wan.android.http

import com.wan.common.constant.Const
import com.wan.baselib.network.HttpsUtils

/**
 * Description: 获取各种类型ApiService
 * Created by cy on 2019/10/11.
 */
object HttpHelper {

    val apiService : ApiService by lazy { HttpsUtils.getRetrofit(Const.BASE_URL).create(ApiService::class.java) }
    val gankService : ApiService by lazy { HttpsUtils.getRetrofit(Const.GANK_URL).create(ApiService::class.java) }
}
