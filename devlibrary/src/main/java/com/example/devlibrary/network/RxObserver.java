package com.example.devlibrary.network;

import com.example.devlibrary.network.exception.ApiException;
import com.example.devlibrary.network.exception.ExceptionHandle;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

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

    protected abstract void onFail(int errorCode, String errorMsg);

    @Override
    public void onComplete() {

    }
}
