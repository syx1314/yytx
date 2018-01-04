package com.bdlm.yytx.module.me;

import com.bdlm.yytx.api.IUserApi;
import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

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
}
