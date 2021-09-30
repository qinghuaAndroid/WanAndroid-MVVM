package com.wan.baselib.network

/**
 * Created by Cy on 2018/9/27.
 */
object SchedulerUtils {
    @JvmStatic
    fun <T : Any> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}