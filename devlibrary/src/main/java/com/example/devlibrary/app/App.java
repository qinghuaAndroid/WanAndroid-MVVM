package com.example.devlibrary.app;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.devlibrary.utils.AutoDensityUtils;
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
