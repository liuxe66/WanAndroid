package com.liuxe.wanandroid.api;

import com.liuxe.banner.Banner;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BannerData;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.liuxe.wanandroid.bean.Collections;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.bean.Tab;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Liuxe on 2018/12/21 0021
 */
public interface WanAndroidApi {
    //基础地址
    public static final String HOST = "http://www.wanandroid.com/";

    //登录
    @POST("user/login")
    Observable<BaseResponse<Login>> postLogin(@Query("username") String username, @Query("password")String password);

    //注册
    @POST("user/register")
    Observable<BaseResponse<Login>> postRegister(@Query("username")String username,@Query("password")String password,@Query("repassword")String repassword);

    //退出登录
    @GET()
    Observable<BaseResponse<Login>> getLogout();

    //首页banner
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> gatBannerList();

    //首页文章
    @GET("article/list/{pageIndex}/json")
    Observable<BaseResponse<Articles>> getArticlaList(@Path("pageIndex") String pageIndex);

    //微信公众号 tab
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<Tab>>> getWxChapters();

    //微信公众号 文章列表
    @GET("wxarticle/list/{parentChapterId}/{pageIndex}/json")
    Observable<BaseResponse<Articles>> getWxList(@Path("parentChapterId") String parentChapterId, @Path("pageIndex") String pageIndex);

    //项目分类
    @GET("project/tree/json")
    Observable<BaseResponse<List<Tab>>> getProjectTree();

    //项目列表
    @GET("project/list/{pageIndex}/json")
    Observable<BaseResponse<Articles>> getProjectList(@Path("pageIndex") String pageIndex , @Query("cid") String cid);

    //热门搜索
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKey>>> getHotKey();

    //搜索列表
    @POST("article/query/{pageIndex}/json")
    Observable<BaseResponse<Articles>> getSerachArticle(@Path("pageIndex") String pageIndex ,@Query("k") String key);

}
