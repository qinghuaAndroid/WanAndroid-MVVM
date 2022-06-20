package com.wan.android.http

import com.wan.android.bean.*
import com.wan.baselib.network.HttpResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

/**
 * @author cy
 * Create at 2020/4/2.
 */
interface ApiService {

    /**
     * banner
     */
    @GET("/banner/json")
    suspend fun getBanner(): HttpResult<MutableList<BannerEntity>>

    /**
     * 获取首页置顶文章数据
     */
    @GET("/article/top/json")
    suspend fun getTopList(): HttpResult<MutableList<ArticleEntity.DatasBean>>

    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(@Path("page") pageNo: Int): HttpResult<ArticleEntity>

    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): HttpResult<Any>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): HttpResult<Any>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    suspend fun unMyCollect(@Path("id") id: Int, @Field("originId") originId: Int): HttpResult<Any>

    /**
     * 获取项目tab
     */
    @GET("/project/tree/json")
    suspend fun getProjectTabList(): HttpResult<MutableList<TabEntity>>

    /**
     * 获取项目tab
     */
    @GET("/wxarticle/chapters/json  ")
    suspend fun getAccountTabList(): HttpResult<MutableList<TabEntity>>

    /**
     * 获取项目列表
     */
    @GET("/project/list/{pageNum}/json")
    suspend fun getProjectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : HttpResult<ArticleEntity>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    suspend fun getAccountList(@Path("id") cid: Int, @Path("pageNum") pageNum: Int)
            : HttpResult<ArticleEntity>

    /**
     * 体系
     */
    @GET("/tree/json")
    suspend fun getSystemList(): HttpResult<MutableList<SystemListEntity>>


    /**
     * 获取项目tab
     */
    @GET("/article/list/{pageNum}/json")
    suspend fun getSystemArticle(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : HttpResult<ArticleEntity>

    /**
     * 导航
     */
    @GET("/navi/json")
    suspend fun getNavigation(): HttpResult<MutableList<NavigationEntity>>

    /**
     * 排名
     */
    @GET("/coin/rank/{pageNum}/json")
    fun getRank(@Path("pageNum") pageNum: Int): Observable<HttpResult<RankEntity>>

    /**
     * 获取个人信息
     */
    @GET("/user/lg/userinfo/json")
    suspend fun getUserInfo(): HttpResult<UserInfoEntity>

    /**
     * 积分记录
     */
    @GET("/lg/coin/list/{pageNum}/json")
    suspend fun getIntegralRecord(@Path("pageNum") pageNum: Int): HttpResult<IntegralRecordEntity>

    /**
     * 广场列表
     */
    @GET("/user_article/list/{pageNum}/json")
    suspend fun getShareArticle(@Path("pageNum") pageNum: Int): HttpResult<ArticleEntity>

    /**
     * 我分享的文章
     */
    @GET("/user/lg/private_articles/{pageNum}/json")
    suspend fun getMyArticle(@Path("pageNum") pageNum: Int): HttpResult<MyArticleEntity>

    /**
     * 删除我分享的文章
     */
    @POST("/lg/user_article/delete/{id}/json")
    fun deleteMyArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 分享文章
     */
    @POST("/lg/user_article/add/json")
    fun shareArticle(
        @Query("title") title: String,
        @Query("link") link: String
    ): Observable<HttpResult<Any>>

    /**
     * 获取收藏文章数据
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectData(@Path("page") pageNo: Int):
            HttpResult<ArticleEntity>

    /**
     * 退出登录
     */
    @GET("/user/logout/json")
    suspend fun logout(): HttpResult<Any>

    /**
     * 搜索热词
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("hotkey/json")
    suspend fun getHotSearchData(): HttpResult<MutableList<HotSearchEntity>>

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page
     * @param key
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(
        @Path("page") page: Int,
        @Field("k") key: String
    ): HttpResult<ArticleEntity>

    /**
     * 问答
     */
    @GET("/wenda/list/{pageNum}/json")
    suspend fun getQuestionList(@Path("pageNum") pageNum: Int): HttpResult<ArticleEntity>

}