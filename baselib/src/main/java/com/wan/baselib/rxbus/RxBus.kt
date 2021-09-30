package com.wan.baselib.rxbus

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subscribers.SerializedSubscriber

enum class RxBus {
    INSTANCE;

    private val bus: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()

    // 发送一个新的事件
    fun post(o: Any) {
        SerializedSubscriber(bus).onNext(o)
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    fun <T : Any> toFlowable(eventType: Class<T>): Flowable<T> {
        return bus.ofType(eventType)
    }

    fun hasSubscribers(): Boolean {
        return bus.hasSubscribers()
    }

}