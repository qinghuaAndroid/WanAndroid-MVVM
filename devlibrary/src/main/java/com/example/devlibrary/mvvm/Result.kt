package com.example.devlibrary.mvvm

import com.example.devlibrary.network.exception.ApiException

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
sealed class Result<out T : Any?> {

    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val exception: ApiException) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}