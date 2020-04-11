package com.example.devlibrary.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.devlibrary.constant.Const
import com.example.devlibrary.event.NetworkChangeEvent
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.utils.NetworkUtil
import com.tencent.mmkv.MMKV

/**
 * Created by chenxz on 2018/8/1.
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private val mmkv by lazy { MMKV.defaultMMKV() }
    private val hasNetwork by lazy { mmkv.decodeBool(Const.KEY_HAS_NETWORK, true) }

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetworkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                LiveEventBusHelper.post(Const.KEY_NETWORK_CHANGE, NetworkChangeEvent(isConnected))
            }
        } else {
            LiveEventBusHelper.post(Const.KEY_NETWORK_CHANGE, NetworkChangeEvent(isConnected))
        }
    }

}