package com.wan.android.provider

import android.content.Context
import androidx.startup.Initializer
import com.wan.baselib.storage.StorageUtil

/**
 *
 * Created by cy on 2023/7/18.
 */
class StorageInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        //初始化项目一些图片、视频、日志等文件存储目录
        StorageUtil.init(context, context.externalCacheDir?.canonicalPath)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LiveBusInitializer::class.java)
    }
}