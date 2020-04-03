package com.example.devlibrary.rxbus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

public enum RxBus {
    INSTANCE;

    private final FlowableProcessor<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    // 发送一个新的事件
    public void post(Object o) {
        new SerializedSubscriber<>(bus).onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }
}