package com.wan.baselib.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @author FQH
 * Create at 2020/6/22.
 */
interface IFragmentCallBack {

    /**
     * 从一个Fragment 跳到另一个Fragment
     *
     * @param tag
     * @param current
     * @param bundle
     */
    fun jump(tag: String, current: Fragment, bundle: Bundle? = null)

    /**
     * 返回第一个Fragment
     */
    fun home()
}