package com.wan.baselib.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>
) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect(collector)
    }
}