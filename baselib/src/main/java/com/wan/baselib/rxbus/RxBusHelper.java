package com.wan.baselib.rxbus;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxBusHelper {
    /**
     * 发布消息
     *
     * @param o
     */
    public static void post(Object o) {
        RxBus.INSTANCE.post(o);
    }

    /**
     * 接收消息,并在主线程处理
     *
     * @param aClass
     * @param disposables 用于存放消息
     * @param listener
     * @param <T>
     */
    public static <T> void doOnMainThread(Class<T> aClass, CompositeDisposable disposables, final OnEventListener<T> listener) {
        disposables.add(RxBus.INSTANCE.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        listener.onEvent(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError(throwable);
                    }
                }));
    }

    public static <T> void doOnMainThread(Class<T> aClass, final OnEventListener<T> listener) {
        Disposable disposable = RxBus.INSTANCE.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        listener.onEvent(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError(throwable);
                    }
                });
    }

    /**
     * 接收消息,并在子线程处理
     *
     * @param aClass
     * @param disposables
     * @param listener
     * @param <T>
     */
    public static <T> void doOnChildThread(Class<T> aClass, CompositeDisposable disposables, final OnEventListener<T> listener) {
        disposables.add(RxBus.INSTANCE.toFlowable(aClass).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        listener.onEvent(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError(throwable);
                    }
                }));
    }

    public static <T> void doOnChildThread(Class<T> aClass, final OnEventListener<T> listener) {
        Disposable disposable = RxBus.INSTANCE.toFlowable(aClass).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        listener.onEvent(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError(throwable);
                    }
                });
    }

    public interface OnEventListener<T> {
        void onEvent(T t);

        void onError(Throwable throwable);
    }

}