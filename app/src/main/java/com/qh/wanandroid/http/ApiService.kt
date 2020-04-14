package com.qh.wanandroid.http

import com.example.devlibrary.network.HttpResult
import com.qh.wanandroid.bean.*
import com.zs.wanandroid.entity.CollectEntity
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author FQH
 * Create at 2020/4/2.
 */
interface ApiService {

    /**
     * banner
     */
    @GET("/banner/json")
    fun getBanner(): Observable<HttpResult<MutableList<BannerEntity>>>

    /**
     * 获取首页置顶文章数据
     */
    @GET("/article/top/json")
    fun getTopList(): Observable<HttpResult<MutableList<ArticleEntity.DatasBean>>>

    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    fun getHomeList(@Path("page") pageNo: Int): Observable<HttpResult<ArticleEntity>>

    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 获取项目tab
     */
    @GET("/project/tree/json")
    fun getProjectTabList(): Observable<HttpResult<MutableList<TabEntity>>>

    /**
     * 获取项目tab
     */
    @GET("/wxarticle/chapters/json  ")
    fun getAccountTabList(): Observable<HttpResult<MutableList<TabEntity>>>

    /**
     * 获取项目列表
     */
    @GET("/project/list/{pageNum}/json")
    fun getProjectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : Observable<HttpResult<ArticleEntity>>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    fun getAccountList(@Path("id") cid: Int, @Path("pageNum") pageNum: Int)
            : Observable<HttpResult<ArticleEntity>>

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
     * 获取个人积分
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getIntegral(): HttpResult<IntegralEntity>

    /**
     * 积分记录
     */
    @GET("/lg/coin/list/{pageNum}/json")
    suspend fun getIntegralRecord(@Path("pageNum") pageNum: Int): HttpResult<IntegralRecordEntity>

    /**
     * 我分享的文章
     */
    @GET("/user/lg/private_articles/{pageNum}/json")
    fun getMyArticle(@Path("pageNum") pageNum: Int): Observable<HttpResult<MyArticleEntity>>

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
    fun getCollectData(@Path("page") pageNo: Int):
            Observable<HttpResult<CollectEntity>>

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

    /**
     * 退出登录
     */
    @GET("/user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{limit}/{page}")
    fun getGankIoData(
        @Path("type") type: String,
        @Path("limit") limit: Int,
        @Path("page") page: Int
    ): Observable<GankIoDataBean>

    /**
     * 搜索热词
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("hotkey/json")
    fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchEntity>>>

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page
     * @param key
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(@Path("page") page: Int,
                         @Field("k") key: String): HttpResult<ArticleEntity>

}