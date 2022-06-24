package com.wan.baselib.utils

import android.content.Context
import android.graphics.Color
import com.wan.baselib.R
import com.tencent.mmkv.MMKV

/**
 * Created by Cy on 2018/8/7.
 */
object SettingUtil {

    private val mmkv by lazy { MMKV.defaultMMKV()!! }

    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        return mmkv.decodeBool("switch_noPhotoMode", false)
    }

    /**
     * 设置是否开启无图模式
     */
    fun setIsNoPhotoMode(flag: Boolean) {
        mmkv.encode("switch_noPhotoMode", flag)
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = ResourcesUtils.getColor(R.color.Blue)
        val color = mmkv.decodeInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        mmkv.encode("color", color)
    }

    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return mmkv.decodeBool("nav_bar", false)
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        mmkv.encode("switch_nightMode", flag)
    }

    /**
     * 获取是否开启夜间模式
     */
    @JvmStatic
    fun getIsNightMode(): Boolean {
        return mmkv.decodeBool("switch_nightMode", false)
    }
}