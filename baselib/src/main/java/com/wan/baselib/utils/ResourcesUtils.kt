package com.wan.baselib.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.wan.baselib.ext.appContext

/**
 * @author cy
 */
object ResourcesUtils {

    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    @JvmStatic
    fun getString(@StringRes id: Int): String {
        return appContext.resources.getString(id)
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return appContext.resources.getStringArray(id)
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(appContext, id)
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(appContext, id)
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    fun getColorStateList(@ColorRes id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(appContext, id)
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    fun getDimen(@DimenRes id: Int): Int {
        return appContext.resources.getDimensionPixelSize(id)
    }
}