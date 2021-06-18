package com.wan.baselib.network;

import com.wan.baselib.network.exception.ApiException;
import com.wan.baselib.network.function.RetryWithDelay;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;

/**
 * Created by Cy on 2017/5/11.
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, HttpResult<T>> handleResult() {
        return new ObservableTransformer<HttpResult<T>, HttpResult<T>>() {
            @NotNull
            @Override
            public ObservableSource<HttpResult<T>> apply(@NonNull Observable<HttpResult<T>> observable) {
                return observable.flatMap(new Function<HttpResult<T>, ObservableSource<HttpResult<T>>>() {
                    @Override
                    public ObservableSource<HttpResult<T>> apply(@NonNull final HttpResult<T> result) throws Exception {
                        if (result.success()) {
                            return Observable.create(new ObservableOnSubscribe<HttpResult<T>>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<HttpResult<T>> e) throws Exception {
                                    try {
                                        e.onNext(result);
                                        e.onComplete();
                                    } catch (Exception ex) {
                                        e.onError(ex);
                                    }
                                }
                            });
                        } else {
                            return Observable.error(new ApiException(result.getErrorCode(), result.getErrorMsg()));
                        }
                    }
                }).retryWhen(new RetryWithDelay()).compose(SchedulerUtils.<HttpResult<T>>ioToMain());
            }
        };
    }
}