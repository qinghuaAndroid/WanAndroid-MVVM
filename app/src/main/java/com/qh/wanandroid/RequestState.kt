package com.qh.wanandroid

/**
 * @author FQH
 * Create at 2019/12/17.
 */

enum class Status {
    SUCCESS,
    ERROR,
}

data class RequestState<out T>(val status: Status, val data: T? = null, val errorCode: Int? = null, val errorMsg: String? = null){

    companion object{
        fun <T> success(data: T? = null) = RequestState(
            Status.SUCCESS,
            data
        )

        fun error(errorMsg: String? = null, errorCode: Int? = null) =
            RequestState(
                Status.ERROR,
                null,
                errorCode,
                errorMsg
            )
    }

    fun isSuccess(): Boolean = status == Status.SUCCESS
    fun isError(): Boolean = status == Status.ERROR
}