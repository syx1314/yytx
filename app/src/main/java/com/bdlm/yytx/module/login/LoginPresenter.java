package com.bdlm.yytx.module.login;


import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.mvp.BasePresenter;

import java.util.List;


/**
 *
 * @author yyj
 * @date 2017/12/25
 */

public class LoginPresenter extends BasePresenter<LoginContact.ILoginView> implements LoginContact.ILoginListener {
    LoginModel model;
    LoginContact.ILoginView loginView;

    public LoginPresenter(LoginContact.ILoginView view) {
        model = new LoginModel();
        attachV(view);
    }

    @Override
    public void attachV(LoginContact.ILoginView view) {
        super.attachV(view);
        this.loginView=view;
    }

    public void sendCode(String phone) {
        model.sendCode(phone, this);
    }

    public void login(String phone, String code) {
        model.login(phone, code, this);
    }

    @Override
    public void codeResponse(ApiResultBean<List<String>> apiResult) {
        if(loginView!=null)
        loginView.sendCodeResult(apiResult);
    }

    @Override
    public void loginResule(LoginResponse loginResponse) {
        if(loginView!=null)
        loginView.loginResult(loginResponse);
    }

    @Override
    public void error(String msg) {
            loginView.error(msg);
    }
}
