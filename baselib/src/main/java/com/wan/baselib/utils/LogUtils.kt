package com.wan.baselib.utils

import android.util.Log
import com.wan.baselib.BuildConfig

/**
 * Log工具类
 */
object LogUtils {
    /**
     * 日志输出级别NONE
     */
    const val LEVEL_NONE = 0

    /**
     * 日志输出级别E
     */
    const val LEVEL_ERROR = 1

    /**
     * 日志输出级别W
     */
    const val LEVEL_WARN = 2

    /**
     * 日志输出级别I
     */
    const val LEVEL_INFO = 3

    /**
     * 日志输出级别D
     */
    const val LEVEL_DEBUG = 4

    /**
     * 日志输出级别V
     */
    const val LEVEL_VERBOSE = 5

    /**
     * 日志输出时的TAG
     */
    private const val TAG = "LogUtils"

    /**
     * 是否允许输出log
     */
    private var mDebuggable = if (BuildConfig.DEBUG) LEVEL_VERBOSE else LEVEL_NONE

    /**
     * 设置调试Log开关
     *
     * @param isEnable 是否允许log
     */
    fun setDebuggable(isEnable: Boolean) {
        mDebuggable = if (isEnable) LEVEL_VERBOSE else LEVEL_NONE
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    fun v(msg: String?) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(TAG, msg!!)
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    fun d(msg: String?) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(TAG, msg!!)
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    fun i(msg: String?) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(TAG, msg!!)
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    fun w(msg: String?) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(TAG, msg!!)
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    fun e(msg: String?) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(TAG, msg!!)
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    fun w(tr: Throwable?) {
        w("", tr)
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    fun w(msg: String?, tr: Throwable?) {
        Log.w(TAG, msg, tr)
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    fun e(tr: Throwable?) {
        e("", tr)
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    fun e(msg: String?, tr: Throwable?) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            Log.e(TAG, msg, tr)
        }
    }
}