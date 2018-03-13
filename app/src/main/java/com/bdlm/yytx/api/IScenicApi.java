package com.bdlm.yytx.api;

import com.amap.api.maps.offlinemap.City;
import com.bdlm.yytx.entity.CityBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TicketBean;
import com.bdlm.yytx.entity.TicketListResponse;
import com.bdlm.yytx.entity.TourInfoListBean;
import com.bdlm.yytx.entity.TourInfoListResponse;
import com.trsoft.app.lib.http.ApiResultBean;


import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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


    @GET("Senic/getList")
    Observable<ApiResultBean<ScenicListResponse>> searchScenic(@Query("search_name")  String search_name);

    @FormUrlEncoded
    @POST("Senic/getDetailById")
    Observable<ApiResultBean<ScenicDetailResponse>> scenicDetails(@Field("senic_id") String senic_id);

    @GET("Senic/getCitys")
    Observable<ApiResultBean<List<CityBean>>> getCitys();

    @GET("Senic/getRoundRecommend")
    Observable<ApiResultBean<List<ScenicResponse>>>  nearByScenic(@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("senic_id") String senic_id);

    @GET("Ticket/getList")
    Observable<ApiResultBean<TicketListResponse>> ticketList(@Query("page")String  page);

    @GET("Senic/recommend")
    Observable<ApiResultBean<List<ScenicResponse>>> recommend();
    //景区游玩排行
    @GET("Senic/advanceRank")
    Observable<ApiResultBean<List<ScenicPlaySortBean>>> scenicPlaySort();

    //旅游资讯列表接口
    @GET("App/getTourInfoList")
    Observable<ApiResultBean<TourInfoListResponse>> getTourInfoList(@Query("page") String page);
}
