package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.AppVersion;
import com.bdlm.yytx.entity.ImageBean;
import com.trsoft.app.lib.http.ApiResultBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 系统类的
 * Created by yyj on 2018/1/11.
 */

public interface ISysApi {

    @FormUrlEncoded
    @POST("Ad/getAdList")
    Observable<ApiResultBean<List<ImageBean>>> adList(String position_id);

    @GET("User/get_system_config")
    Observable<ApiResultBean<String>> systemConfig(@Query("type") String type);

    @GET("App/get_system_config")
    Observable<ApiResultBean<String>> systemNotice(@Query("type") String type);


    @FormUrlEncoded
    @POST("App/upVer")
    Observable<ApiResultBean<AppVersion>> checkAppVersion(@Field("version") String version);
}
