package com.wan.baselib.network;

import androidx.annotation.NonNull;

import com.wan.baselib.network.exception.ApiException;
import com.wan.baselib.network.exception.ExceptionHandle;

import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * Created by fanqh on 2017/11/14.
 */

public abstract class RxObserver<T> extends DisposableObserver<HttpResult<T>> {

    @Override
    protected void onStart() {

    }

    @Override
    public void onNext(@NonNull HttpResult<T> tBaseModel) {
        onSuccess(tBaseModel.getData());
    }

    /**
     * 网络请求成功回调
     *
     * @param t 响应对象
     */
    protected abstract void onSuccess(T t);

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            onFail(apiException.getErrorCode(), apiException.getMessage());
        } else {
            String errorMsg = ExceptionHandle.Companion.handleException(e);
            onFail(-1, errorMsg);
        }
    }

    /**
     * 网络请求失败回调
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    protected abstract void onFail(int errorCode, String errorMsg);

    @Override
    public void onComplete() {

    }
}
