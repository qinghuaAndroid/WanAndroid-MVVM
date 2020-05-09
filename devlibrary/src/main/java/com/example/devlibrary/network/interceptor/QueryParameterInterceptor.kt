package com.example.devlibrary.network.interceptor

import com.example.devlibrary.utils.getDeviceBrand
import com.example.devlibrary.utils.getSystemVersion
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc QueryParameterInterceptor 设置公共参数（eg: "/banner/json?sysVersion=7.1.1&deviceBrand=OPPO）
 */
class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
            // Provide your custom parameter here
            .addQueryParameter("sysVersion", getSystemVersion())//系统版本号
            .addQueryParameter("deviceBrand", getDeviceBrand())//手机厂商
            .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}