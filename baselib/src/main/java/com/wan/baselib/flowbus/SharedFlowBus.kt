package com.wan.baselib.flowbus

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.ConcurrentHashMap

object SharedFlowBus {

    private var events = ConcurrentHashMap<String, MutableSharedFlow<Any>>()
    private var stickyEvents = ConcurrentHashMap<Any, MutableSharedFlow<Any>>()

    fun <T> with(key: String): MutableSharedFlow<T> {
        if (!events.containsKey(key)) {
            events[key] = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
        }
        return events[key] as MutableSharedFlow<T>
    }

    fun <T> withSticky(key: String): MutableSharedFlow<T> {
        if (!stickyEvents.containsKey(key)) {
            stickyEvents[key] = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
        }
        return stickyEvents[key] as MutableSharedFlow<T>
    }

    fun <T> on(key: String): LiveData<T> {
        return with<T>(key).asLiveData()
    }

    fun <T> onSticky(key: String): LiveData<T> {
        return withSticky<T>(key).asLiveData()
    }

}