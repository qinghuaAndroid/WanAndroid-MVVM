package com.wan.login.http

import com.wan.baselib.network.HttpResult
import com.wan.login.bean.UserEntity
import retrofit2.http.*

/**
 * @author cy
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
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): HttpResult<Any>

}