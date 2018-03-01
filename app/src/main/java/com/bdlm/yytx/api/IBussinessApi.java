package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.http.ApiResultBean;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by yyj on 2018/2/2.
 */

public interface IBussinessApi {
    //提交商家审核信息
    @FormUrlEncoded
    @POST("Partner/reg")
    Observable<ApiResultBean<BusinessBean>> submitApprove(@Field("mobile") String mobile, @Field("shop_name") String shop_name
            , @Field("manage_type") String manage_type, @Field("longitude") String longitude, @Field("latitude") String latitude, @Field("address") String address
            , @Field("discount_info") String discount_info, @Field("special_explain") String special_explain, @Field("logo_img") String logo_img);

    @GET("Partner/getManageType")
    Observable<ApiResultBean<List<ManagerTypeBean>>> getManagerType();

    //上传图片
    @Multipart
    @POST("Partner/uploadPic")
    Observable<ApiResultBean<UploadPicRespon>>  uploadPic(@Part("type") RequestBody type, @Part("ad_img\"; filename=\"head_icon.png") RequestBody file );


    @FormUrlEncoded
    @POST("Partner/getList")
    Observable<ApiResultBean<BusinessListResponse>> bussinessList( @Field("manage_type") String manage_type, @Field("longitude") String longitude, @Field("latitude") String latitude,@Field("page") String page);

}
