package com.qh.wanandroid.http

import com.example.devlibrary.network.HttpsUtils
import com.qh.wanandroid.constant.Const

/**
 * Description: 获取各种类型ApiService
 * Created by FQH on 2019/10/11.
 */
object HttpHelper {

    val apiService : ApiService by lazy { HttpsUtils.getRetrofit(Const.BASE_URL).create(ApiService::class.java) }
    val gankService : ApiService by lazy { HttpsUtils.getRetrofit(Const.GANK_URL).create(ApiService::class.java) }
    val sogouService : ApiService by lazy { HttpsUtils.getRetrofit(Const.SOGOU_URL).create(ApiService::class.java) }
}
