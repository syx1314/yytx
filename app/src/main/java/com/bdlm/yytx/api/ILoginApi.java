package com.bdlm.yytx.api;



import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 小呆呆 on 2017/12/23 0023.
 */

public interface ILoginApi {
    @FormUrlEncoded
    @POST("User/login")
    Observable<ApiResultBean<LoginResponse>> login(@Field("mobile") String mobile, @Field("code") String code, @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("User/sendVerCode")
    Observable<ApiResultBean<List<String>>> sendCode(@Field("mobile") String mobile);
}
