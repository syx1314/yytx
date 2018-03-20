package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.AppVersion;
import com.bdlm.yytx.entity.HomeURLBean;
import com.bdlm.yytx.entity.ImageBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.http.ApiResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 系统类的
 * Created by yyj on 2018/1/11.
 */

public interface ISysApi {

    @FormUrlEncoded
    @POST("Ad/getAdList")
    Observable<ApiResultBean<List<ImageBean>>> adList(@Field("position_id") String position_id);

    @GET("User/get_system_config")
    Observable<ApiResultBean<String>> systemConfig(@Query("type") String type);

    @GET("App/get_system_config")
    Observable<ApiResultBean<String>> systemNotice(@Query("type") String type);


    @FormUrlEncoded
    @POST("App/upVer")
    Observable<ApiResultBean<AppVersion>> checkAppVersion(@Field("version") String version);

    @FormUrlEncoded
    @POST("User/feedback")
    Observable<ApiResultBean<String>> feedBack(@Field("content") String content,@Field("mobile") String mobile);

    @GET("App/getShopUrl")
    Observable<ApiResultBean<HomeURLBean>> getShopUrl();

    //上传图片
    @Multipart
    @POST("User/editAvatar")
    Observable<ApiResultBean<UploadPicRespon>>  uploadPic(@Part("ad_img\"; filename=\"head_icon.png") RequestBody file );


}
