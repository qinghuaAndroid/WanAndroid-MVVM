package com.example.devlibrary.network.exception;

/**
 * Created by fanqh on 2017/10/26.
 */

public class ApiException extends RuntimeException {

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    private int errorCode;

    public ApiException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
