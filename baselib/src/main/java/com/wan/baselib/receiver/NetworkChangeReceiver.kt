package com.wan.baselib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tencent.mmkv.MMKV
import com.wan.baselib.constant.Const
import com.wan.baselib.flowbus.SharedFlowBus
import com.wan.baselib.utils.NetworkUtil

/**
 * Created by chenxz on 2018/8/1.
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private val mmkv by lazy { MMKV.defaultMMKV()!! }
    private val hasNetwork get() = mmkv.decodeBool(Const.KEY_HAS_NETWORK, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetworkUtil.isNetworkConnected(context)
        if (isConnected != hasNetwork) {
            SharedFlowBus.with<Boolean>(Const.KEY_NETWORK_CHANGE).tryEmit(isConnected)
        }
    }
}