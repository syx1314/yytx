package com.bdlm.yytx.api;

import com.amap.api.maps.offlinemap.City;
import com.bdlm.yytx.entity.CityBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.http.ApiResultBean;


import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 * 关于景区的
 * Created by yyj on 2018/1/4.
 */

public interface IScenicApi {

    @FormUrlEncoded
    @POST("Senic/getList")
    Observable<ApiResultBean<ScenicListResponse>> getScenicList(@Field("longitude") String longitude, @Field("latitude") String latitude, @Field("passport_type") int passport_type, @Field("city_id") int city_id, @Field("page") int page);

    @FormUrlEncoded
    @POST("Senic/getDetailById")
    Observable<ApiResultBean<ScenicDetailResponse>> scenicDetails(@Field("senic_id") String senic_id);

    @GET("Senic/getCitys")
    Observable<ApiResultBean<List<CityBean>>> getCitys();

}
