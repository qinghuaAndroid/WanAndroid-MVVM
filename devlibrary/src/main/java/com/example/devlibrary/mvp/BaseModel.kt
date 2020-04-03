package com.example.devlibrary.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by chenxz on 2018/8/18.
 */
abstract class BaseModel : IModel, LifecycleObserver {

    override fun onDetach() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}