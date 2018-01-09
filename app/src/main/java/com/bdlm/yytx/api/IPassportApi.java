package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.PassportTypeBean;
import com.trsoft.app.lib.http.ApiResultBean;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * g关于护照 年卡的
 * Created by yyj on 2018/1/9.
 */

public interface IPassportApi {

    @GET("Senic/passportTypeList")
    Observable<ApiResultBean<List<PassportTypeBean>>> getPassportType();
}
