package com.wan.baselib.mvvm

import com.wan.baselib.network.HttpResult
import com.wan.baselib.network.exception.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> HttpResult<T>): HttpResult<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            Result.Error(ApiException(e.message))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: HttpResult<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            if (response.success()) {
                successBlock?.let { it() }
                Result.Success(response.data)
            } else {
                errorBlock?.let { it() }
                Result.Error(ApiException(response.errorCode, response.errorMsg))
            }
        }
    }

}