package com.qh.wanandroid.ext

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.common.constant.Const
import com.qh.wanandroid.ui.login.LoginActivity
import com.tencent.mmkv.MMKV

/**
 * @author FQH
 * Create at 2020/4/13.
 */
internal fun Fragment.startActivity(clazz: Class<*>, needLogin: Boolean = false) {
    val isLogin = MMKV.defaultMMKV().decodeBool(Const.IS_LOGIN, false)
    if (needLogin && isLogin.not()) {
        startActivity(Intent(activity, LoginActivity::class.java))
    } else {
        startActivity(Intent(activity, clazz))
    }
}

internal fun Activity.startActivity(clazz: Class<*>, needLogin: Boolean = false) {
    val isLogin = MMKV.defaultMMKV().decodeBool(Const.IS_LOGIN, false)
    if (needLogin && isLogin.not()) {
        startActivity(Intent(this, LoginActivity::class.java))
    } else {
        startActivity(Intent(this, clazz))
    }
}