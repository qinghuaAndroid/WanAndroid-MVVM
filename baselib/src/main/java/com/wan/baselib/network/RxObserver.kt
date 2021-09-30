package com.wan.baselib.network

import com.wan.baselib.network.exception.ApiException
import com.wan.baselib.network.exception.ExceptionHandler
import io.reactivex.rxjava3.observers.DisposableObserver

/**
 * Created by fanqh on 2017/11/14.
 */
abstract class RxObserver<T> : DisposableObserver<HttpResult<T>>() {

    override fun onStart() {}

    override fun onNext(tBaseModel: HttpResult<T>) {
        onSuccess(tBaseModel.data)
    }

    /**
     * 网络请求成功回调
     *
     * @param t 响应对象
     */
    protected abstract fun onSuccess(t: T?)

    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (e is ApiException) {
            onFail(e.errorCode, e.message)
        } else {
            val errorMsg = ExceptionHandler.handleException(e)
            onFail(-1, errorMsg)
        }
    }

    /**
     * 网络请求失败回调
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    protected abstract fun onFail(errorCode: Int, errorMsg: String?)

    override fun onComplete() {}
}