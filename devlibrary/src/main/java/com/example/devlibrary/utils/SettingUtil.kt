package com.example.devlibrary.utils

import android.graphics.Color
import com.example.devlibrary.R

/**
 * Created by Cy on 2018/8/7.
 */
object SettingUtil {

    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        return SpUtils.get("switch_noPhotoMode", false)
    }

    /**
     * 设置是否开启无图模式
     */
    fun setIsNoPhotoMode(flag: Boolean) {
        return SpUtils.put("switch_noPhotoMode", flag)
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = ResourcesUtils.getColor(R.color.colorAccent)
        val color = SpUtils.get("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        SpUtils.put("color", color)
    }

    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return SpUtils.get("nav_bar", false)
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        SpUtils.put("switch_nightMode", flag)
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return SpUtils.get("switch_nightMode", false)
    }
}