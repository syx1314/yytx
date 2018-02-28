package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.http.ApiResultBean;

import java.io.File;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
            , @Field("discount_info") String discount_info, @Field("describe") String describe, @Field("logo_img") String logo_img);

    @GET("Partner/getManageType")
    Observable<ApiResultBean<List<ManagerTypeBean>>> getManagerType();
    @FormUrlEncoded
    @POST("Partner/uploadPic")
    Observable<ApiResultBean<UploadPicRespon>>  uploadPic(@Field("type") String type, @Field("_FILE") File _FILE );

}
