package com.wan.baselib.network

import androidx.annotation.NonNull
import com.wan.baselib.network.exception.ApiException
import com.wan.baselib.network.exception.ExceptionHandler
import io.reactivex.rxjava3.observers.DisposableObserver

/**
 * Created by fanqh on 2017/11/14.
 */
abstract class RxNonNullObserver<T> : DisposableObserver<HttpResult<T>>() {

    override fun onStart() {}

    override fun onNext(tBaseModel: HttpResult<T>) {
        if (tBaseModel.data != null) {
            onSuccess(tBaseModel.data)
        } else {
            onFailed(-1,"data cannot be null")
        }
    }

    /**
     * 网络请求成功回调
     *
     * @param t 响应对象
     */
    protected abstract fun onSuccess(@NonNull t: T)

    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (e is ApiException) {
            onFailed(e.errorCode, e.message)
        } else {
            val apiException = ExceptionHandler.handleException(e)
            onFailed(apiException.errorCode, apiException.message)
        }
    }

    /**
     * 网络请求失败回调
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    protected abstract fun onFailed(errorCode: Int, errorMsg: String?)

    override fun onComplete() {}
}