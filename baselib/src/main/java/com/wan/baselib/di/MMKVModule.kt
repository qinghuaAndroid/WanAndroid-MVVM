package com.wan.baselib.di

import android.content.Context
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MMKVModule {

    @Provides
    @Singleton
    fun provideMMKV(): MMKV {
        return MMKV.defaultMMKV()
    }
}


@EntryPoint
@InstallIn(SingletonComponent::class)
interface MMKVEntryPoint {
    fun getMMKV(): MMKV
}

fun getMMKV(context: Context): MMKV {
    return EntryPointAccessors.fromApplication(context, MMKVEntryPoint::class.java).getMMKV()
}