package com.wan.baselib.app;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.wan.baselib.utils.AutoDensityUtils;
import com.wan.baselib.utils.SettingUtil;
import com.tencent.mmkv.MMKV;

public class App extends Application {

    public static Context sContext;
    public static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = getApplicationContext();
        AutoDensityUtils.init();
        MMKV.initialize(this);
        initTheme();
    }

    private void initTheme() {
        // 获取当前的主题
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // clear Glide cache
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        // trim memory
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // low memory clear Glide cache
        Glide.get(this).clearMemory();
    }
}
