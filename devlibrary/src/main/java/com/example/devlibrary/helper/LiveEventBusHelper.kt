package com.example.devlibrary.helper

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Description: LiveEventBus 辅助类
 * LiveEventBus是一款Android消息总线，基于LiveData，具有生命周期感知能力，
 * 支持Sticky，支持AndroidX，支持跨进程，支持跨APP
 *
 * Created by FQH on 2019/10/11.
 */
object LiveEventBusHelper {

    /**
     * 发送一个消息，支持前台线程、后台线程发送
     *
     * @param value
     */
    fun <T> post(
        key: String,
        value: T
    ) {
        LiveEventBus.get(key).post(value)
    }

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     *
     * @param owner
     * @param observer
     */
    fun <T> observe(
        key: String,
        type: Class<T>, @NonNull owner: LifecycleOwner, @NonNull observer: Observer<T>
    ) {
        LiveEventBus.get(key, type).observe(owner, observer)
    }

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     *
     * @param owner
     * @param observer
     */
    fun <T> observeSticky(
        key: String,
        type: Class<T>, @NonNull owner: LifecycleOwner, @NonNull observer: Observer<T>
    ) {
        LiveEventBus.get(key, type).observeSticky(owner, observer)
    }
}