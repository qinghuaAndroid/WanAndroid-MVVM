package com.wan.baselib.network

import com.wan.baselib.network.exception.ApiException
import com.wan.baselib.network.function.RetryWithDelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.functions.Function

/**
 * Created by Alexis.Shelton on 30/9/2021.
 */
object RxHelper {
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
    </T> */
    @JvmStatic
    fun <T> handleResult(): ObservableTransformer<HttpResult<T>, HttpResult<T>> {
        return object : ObservableTransformer<HttpResult<T>, HttpResult<T>> {
            override fun apply(upstream: Observable<HttpResult<T>>): ObservableSource<HttpResult<T>> {
                return upstream.flatMap(object :
                    Function<HttpResult<T>, ObservableSource<HttpResult<T>>> {
                    override fun apply(result: HttpResult<T>): ObservableSource<HttpResult<T>> {
                        return if (result.success()) {
                            Observable.create<HttpResult<T>> { emitter ->
                                try {
                                    emitter.onNext(result)
                                    emitter.onComplete()
                                } catch (ex: Exception) {
                                    emitter.onError(ex)
                                }
                            }
                        } else {
                            return Observable.error(ApiException(result.errorCode, result.errorMsg))
                        }
                    }
                }).retryWhen(RetryWithDelay()).compose(SchedulerUtils.ioToMain())
            }
        }
    }
}