package com.wan.baselib.rxbus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

object RxBusHelper {
    /**
     * 发布消息
     *
     * @param o
     */
    fun post(o: Any) {
        RxBus.INSTANCE.post(o)
    }

    /**
     * 接收消息,并在主线程处理
     *
     * @param aClass
     * @param disposables 用于存放消息
     * @param listener
     * @param <T>
    </T> */
    fun <T : Any> doOnMainThread(
        aClass: Class<T>,
        disposables: CompositeDisposable,
        listener: OnEventListener<T>
    ) {
        disposables.add(RxBus.INSTANCE.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t -> listener.onEvent(t) }) { throwable -> listener.onError(throwable) })
    }

    fun <T : Any> doOnMainThread(aClass: Class<T>, listener: OnEventListener<T>) {
        val disposable = RxBus.INSTANCE.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t -> listener.onEvent(t) }) { throwable -> listener.onError(throwable) }
    }

    /**
     * 接收消息,并在子线程处理
     *
     * @param aClass
     * @param disposables
     * @param listener
     * @param <T>
    </T> */
    fun <T : Any> doOnChildThread(
        aClass: Class<T>,
        disposables: CompositeDisposable,
        listener: OnEventListener<T>
    ) {
        disposables.add(RxBus.INSTANCE.toFlowable(aClass).subscribeOn(Schedulers.newThread())
            .subscribe({ t -> listener.onEvent(t) }) { throwable -> listener.onError(throwable) })
    }

    fun <T : Any> doOnChildThread(aClass: Class<T>, listener: OnEventListener<T>) {
        val disposable = RxBus.INSTANCE.toFlowable(aClass).subscribeOn(Schedulers.newThread())
            .subscribe({ t -> listener.onEvent(t) }) { throwable -> listener.onError(throwable) }
    }

    interface OnEventListener<T> {
        fun onEvent(t: T)
        fun onError(throwable: Throwable?)
    }
}