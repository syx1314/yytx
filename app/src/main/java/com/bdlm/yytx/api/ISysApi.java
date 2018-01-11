package com.bdlm.yytx.api;

import com.bdlm.yytx.entity.ImageBean;
import com.trsoft.app.lib.http.ApiResultBean;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 系统类的
 * Created by yyj on 2018/1/11.
 */

public interface ISysApi {

    @FormUrlEncoded
    @POST("Ad/getAdList")
    Observable<ApiResultBean<List<ImageBean>>> adList(String position_id);
}
