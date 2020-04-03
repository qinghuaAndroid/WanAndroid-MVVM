package com.example.devlibrary.network;

import com.example.devlibrary.network.exception.ApiException;
import com.example.devlibrary.network.function.RetryWithDelay;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        return new ObservableTransformer<HttpResult<T>, HttpResult<T>>(){
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
                        }else {
                            return Observable.error(new ApiException(result.getErrorCode(), result.getErrorMsg()));
                        }
                    }
                }).retryWhen(new RetryWithDelay()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}