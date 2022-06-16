package com.wan.baselib.utils

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.wan.baselib.app.App
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject

/**
 * @author cy
 */
object ResourcesUtils {

    private val context by inject<Application>(Application::class.java)
    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    @JvmStatic
    fun getString(@StringRes id: Int): String {
        return context.resources.getString(id)
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    fun getColorStateList(@ColorRes id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, id)
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    fun getDimen(@DimenRes id: Int): Int {
        return context.resources.getDimensionPixelSize(id)
    }
}