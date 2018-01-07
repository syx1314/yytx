package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.http.ApiResultBean;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yyj on 2018/1/4.
 */

public interface IScenicApi {

    @FormUrlEncoded
    @POST("Senic/getList")
    Observable<ApiResultBean<ScenicListResponse>> getScenicList(@Field("longitude") double longitude, @Field("latitude") double latitude, @Field("passport_type") int passport_type, @Field("city_id") int city_id, @Field("page") int page);

    @FormUrlEncoded
    @POST("Senic/getDetailById")
    Observable<ApiResultBean<ScenicDetailResponse>> scenicDetails(@Field("senic_id") String senic_id);


}
