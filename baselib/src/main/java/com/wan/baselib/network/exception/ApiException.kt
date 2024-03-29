package com.wan.baselib.network.exception

/**
 * Created by cy on 2017/10/26.
 */
class ApiException : RuntimeException {

    var errorCode = 0

    constructor(message: String?) : super(message)

    constructor(errorCode: Int, message: String?) : super(message) {
        this.errorCode = errorCode
    }

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}