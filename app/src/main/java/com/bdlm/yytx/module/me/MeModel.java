package com.bdlm.yytx.module.me;

import com.bdlm.yytx.api.ISysApi;
import com.bdlm.yytx.api.IUserApi;
import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.MyLog;

/**
 * Created by yyj on 2018/1/4.
 */

public class MeModel extends MeContact.IMeModel {


    @Override
    void getUserInfo(final MeContact.IMeListener listener) {
        Subscribe(getApiService(IUserApi.class).getUserInfo(), new IApiReturn<UserInfoBean>() {
            @Override
            public void run(ApiResultBean<UserInfoBean> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode())&&apiResult.getData() != null) {
                        listener.userInfo(apiResult.getData());
                    }else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if (listener != null)
                    listener.error(message);
            }
        });
    }

    @Override
    void logout(final MeContact.IMeListener listener) {
        Subscribe(getApiService(IUserApi.class).logout(), new IApiReturn<String>() {
            @Override
            public void run(ApiResultBean<String> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode())) {
                        listener.logoutResult(apiResult.getMsg());
                    }else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if(listener!=null)
                listener.error(message);
            }
        });
    }

    @Override
    void submitFeedBack(String mobile, String content, final MeContact.IMeListener listener) {
        Subscribe(getApiService(ISysApi.class).feedBack(content, mobile), new IApiReturn<String>() {
            @Override
            public void run(ApiResultBean<String> apiResult) {

                if (listener != null) {
                    if (isSuccess(apiResult.getCode())) {
                        listener.feedBack(apiResult.getMsg());
                    }else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if(listener!=null)
                    listener.error(message);
            }
        });
    }


}
