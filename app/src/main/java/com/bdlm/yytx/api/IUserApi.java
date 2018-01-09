package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.http.ApiResultBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 关于用户的
 * Created by Adim on 2018/1/2.
 */

public interface IUserApi {

    @GET("User/getInfo")
    Observable<ApiResultBean<UserInfoBean>> getUserInfo();

}
