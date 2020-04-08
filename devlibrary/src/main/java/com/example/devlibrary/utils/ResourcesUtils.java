package com.example.devlibrary.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.devlibrary.app.App;

/**
 * @author FQH
 */
public class ResourcesUtils {
    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    public static String getString(int id) {
        return App.sContext.getResources().getString(id);
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    public static String[] getStringArray(int id) {
        return App.sContext.getResources().getStringArray(id);
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(App.sContext, id);
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    public static int getColor(int id) {
        return ContextCompat.getColor(App.sContext, id);
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    public static ColorStateList getColorStateList(int id) {
        return ContextCompat.getColorStateList(App.sContext, id);
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    public static int getDimen(int id) {
        return App.sContext.getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

}
