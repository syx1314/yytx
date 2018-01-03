package com.bdlm.yytx.module.login;

import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;


/**
 * 登录契约类
 * Created by yyj on 2017/12/28.
 */

public interface LoginContact {

    public interface ILoginListener extends IBaseListener {
        void codeResponse(ApiResultBean<List<String>> apiResult);
        void loginResule(LoginResponse loginResponse);

    }

    public interface ILoginView extends IBaseView {
        void sendCodeResult(ApiResultBean<List<String>> apiResult) ;
        void loginResult(LoginResponse loginResponse);
    }
    public abstract  class ILoginModel extends BaseModel {


        abstract void sendCode(String phone, ILoginListener loginListener);
        abstract void login(String phone, String code, ILoginListener loginListener);
    }

}
