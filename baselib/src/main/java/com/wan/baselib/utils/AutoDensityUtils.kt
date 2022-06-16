package com.wan.baselib.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * Created by Cy on 2018/9/20.
 *
 * 感谢今日头条提供的方案  https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 */
object AutoDensityUtils {
    // android中的dp在渲染前会将dp转为px，计算公式：
    // px = density * dp;
    // density = dpi / 160;
    // px = dp * (dpi / 160)
    private var widthUiPx = 750f // 设计图宽度的像素
    private var heightUiPx = 1334f // 设计图高度的像素
    private var sizeUi = 4.7f // 设计图屏幕的尺寸
    private var densityUi = 0f
    private var targetUiDpi = 0.0
    private var widthUiDp = 360f
    private var sNonCompatDensity = 0f // 原始的Density
    private var sNonCompatScaledDensity = 0f // 原始的ScaledDensity

    /**
     * 初始化
     */
    fun init() {
        targetUiDpi =
            Math.sqrt((widthUiPx * widthUiPx + heightUiPx * heightUiPx).toDouble()) / sizeUi
        densityUi = (targetUiDpi / 160).toFloat()
        widthUiDp = widthUiPx / densityUi
    }

    /**
     * 初始化
     * @param widthUiPx
     * @param heightUiPx
     * @param sizeUi
     */
    fun init(widthUiPx: Float, heightUiPx: Float, sizeUi: Float) {
        AutoDensityUtils.widthUiPx = widthUiPx
        AutoDensityUtils.heightUiPx = heightUiPx
        AutoDensityUtils.sizeUi = sizeUi
        init()
    }

    /**
     * @param activity
     * @param application
     */
    fun setCustomDensity(activity: Activity, application: Application) {
        val appDisplayMetrics = application.resources.displayMetrics
        if (sNonCompatDensity == 0f) {
            sNonCompatDensity = appDisplayMetrics.density
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
        val targetDensity = appDisplayMetrics.widthPixels / widthUiDp
        val targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaledDensity
        appDisplayMetrics.densityDpi = targetDensityDpi
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}