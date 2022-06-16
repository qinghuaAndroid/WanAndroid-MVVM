package com.wan.baselib.network;

import com.wan.baselib.network.exception.ErrorStatus;

/**
 * <p>
 *   所有响应实体类的基类
 * </p>
 * Created by fanqh on 2017/11/14.
 */

public class HttpResult<T> {
    private String errorMsg;
    private T data;
    private int errorCode;

    public boolean success() {
        return errorCode == ErrorStatus.SUCCESS;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
