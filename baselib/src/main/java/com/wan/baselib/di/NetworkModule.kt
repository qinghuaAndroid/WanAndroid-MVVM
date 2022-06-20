package com.wan.baselib.di

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.wan.baselib.BuildConfig
import com.wan.baselib.constant.Const
import com.wan.baselib.network.interceptor.HeaderInterceptor
import com.wan.baselib.network.interceptor.QueryParameterInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        val httpCacheDir = File(context.cacheDir, "api_cache")
        val cache = Cache(httpCacheDir, (10 * 1024 * 1024).toLong())

        val cookieJar: ClearableCookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(QueryParameterInterceptor())
            .cookieJar(cookieJar)
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
    }
}