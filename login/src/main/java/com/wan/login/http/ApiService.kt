package com.wan.login.http

import com.example.devlibrary.network.HttpResult
import com.wan.login.bean.UserEntity
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author FQH
 * Create at 2020/4/2.
 */
interface ApiService {
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): HttpResult<UserEntity>

    /**
     * 注册
     */
    @POST("/user/register")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Observable<HttpResult<Any>>

}