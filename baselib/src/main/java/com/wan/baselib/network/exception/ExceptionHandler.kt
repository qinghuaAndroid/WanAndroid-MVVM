package com.wan.baselib.network.exception

import com.google.gson.JsonParseException

import org.json.JSONException

import java.net.ConnectException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by xuhao on 2017/12/5.
 * desc: 异常处理类
 */

class ExceptionHandler {

    companion object {
         private var errorCode = ErrorStatus.UNKNOWN_ERROR
         private var errorMsg = "请求失败，请稍后重试"

        fun handleException(e: Throwable): ApiException {
            e.printStackTrace()
            when (e) {
                is SocketTimeoutException -> {//网络超时
                    errorMsg = "网络连接异常"
                    errorCode = ErrorStatus.NETWORK_ERROR
                }
                is ConnectException -> { //均视为网络错误
                    errorMsg = "网络连接异常"
                    errorCode = ErrorStatus.NETWORK_ERROR
                }
                is JsonParseException, is JSONException, is ParseException -> {   //均视为解析错误
                    errorMsg = "数据解析异常"
                    errorCode = ErrorStatus.SERVER_ERROR
                }
                is UnknownHostException -> {
                    errorMsg = "网络连接异常"
                    errorCode = ErrorStatus.NETWORK_ERROR
                }
                is IllegalArgumentException -> {
                    errorMsg = "参数错误"
                    errorCode = ErrorStatus.SERVER_ERROR
                }
                else -> {//未知错误
                    errorMsg = "请求失败，请稍后重试"
                    errorCode = ErrorStatus.UNKNOWN_ERROR
                }
            }
            return ApiException(errorCode, errorMsg)
        }
    }
}
