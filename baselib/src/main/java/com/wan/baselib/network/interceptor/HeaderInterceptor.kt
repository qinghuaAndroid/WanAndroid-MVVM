package com.wan.baselib.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by Cy on 2017/5/26.
 * 添加请求头
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .header("Content-Type", "application/json;charset=utf8")
            .header("Accept", "application/json")
            .method(originalRequest.method, originalRequest.body)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}