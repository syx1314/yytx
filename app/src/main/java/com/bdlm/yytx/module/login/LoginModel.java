package com.bdlm.yytx.module.login;

import com.bdlm.yytx.api.ILoginApi;
import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;


import java.util.List;


/**
 * Created by yyj on 2017/12/25.
 */

public class LoginModel extends LoginContact.ILoginModel {


    @Override
    public void sendCode(String phone, final LoginContact.ILoginListener loginListener) {

        //网络访问
        Subscribe(getApiService(ILoginApi.class).sendCode(phone), new IApiReturn<List<String>>() {
            @Override
            public void run(ApiResultBean<List<String>> apiResult) {
                if (loginListener != null) {
                    if (isSuccess(apiResult.getCode())) {
                        loginListener.codeResponse(apiResult);
                    } else {
                        loginListener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if (loginListener != null) {
                    loginListener.error(message);
                }
            }
        });

    }

    @Override
    public void login(String phone, String code, final LoginContact.ILoginListener loginListener) {
        Subscribe(getApiService(ILoginApi.class).login(phone, code), new IApiReturn<LoginResponse>() {
            @Override
            public void run(ApiResultBean<LoginResponse> apiResult) {
                if (loginListener != null) {
                    loginListener.loginResule(apiResult.getData());
                }
            }

            @Override
            public void error(String message) {
                if (loginListener != null) {
                    loginListener.error(message);
                }
            }
        });
    }

}
