package com.wan.baselib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by Cy on 2018/9/20.
 *
 * 感谢今日头条提供的方案  https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 */
public class AutoDensityUtils {

    // android中的dp在渲染前会将dp转为px，计算公式：
    // px = density * dp;
    // density = dpi / 160;
    // px = dp * (dpi / 160)

    private static float widthUiPx = 750f; // 设计图宽度的像素
    private static float heightUiPx = 1334f; // 设计图高度的像素
    private static float sizeUi = 4.7f; // 设计图屏幕的尺寸
    private static float densityUi = 0f;
    private static double targetUiDpi = 0.0;
    private static float widthUiDp = 360f;

    private static float sNonCompatDensity = 0f; // 原始的Density
    private static float sNonCompatScaledDensity = 0f; // 原始的ScaledDensity

    /**
     * 初始化
     */
    public static void init() {
        targetUiDpi = Math.sqrt((double) (widthUiPx * widthUiPx + heightUiPx * heightUiPx)) / sizeUi;
        densityUi = (float) (targetUiDpi / 160);
        widthUiDp = widthUiPx / densityUi;
    }

    /**
     * 初始化
     * @param widthUiPx
     * @param heightUiPx
     * @param sizeUi
     */
    public static void init(float widthUiPx, float heightUiPx, float sizeUi) {
        AutoDensityUtils.widthUiPx = widthUiPx;
        AutoDensityUtils.heightUiPx = heightUiPx;
        AutoDensityUtils.sizeUi = sizeUi;
        init();
    }

    /**
     * @param activity
     * @param application
     */
    public static void setCustomDensity(Activity activity, final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNonCompatDensity == 0f) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        float targetDensity = appDisplayMetrics.widthPixels / widthUiDp;
        float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }


}
