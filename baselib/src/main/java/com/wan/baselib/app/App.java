package com.wan.baselib.app;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

import com.bumptech.glide.Glide;

public class App extends Application {

    public static Application application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //application初始化必须在这里， 不能放在onCreate中，
        //否则部分初始化依赖Application的第三方库初始化时会奔溃
        application = this;
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

    public static Context context() {
        return application.getApplicationContext();
    }
}
