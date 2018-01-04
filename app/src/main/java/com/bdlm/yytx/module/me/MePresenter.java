package com.bdlm.yytx.module.me;

import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;

/**
 * Created by yyj on 2018/1/4.
 */

public class MePresenter extends BasePresenter<MeContact.IMeView> implements MeContact.IMeListener {

    MeContact.IMeView meView;
    MeModel meModel;

    public MePresenter(MeContact.IMeView view) {
        this.meModel = new MeModel();
        attachV(view);
    }



    @Override
    public void attachV(MeContact.IMeView view) {
        meView = view;
    }


    public void userInfo() {
        meModel.getUserInfo(this);
    }

    @Override
    public void error(String msg) {
        if(meView!=null)
        meView.error(msg);
    }

    @Override
    public void userInfo(UserInfoBean userInfoBean) {
        if(meView!=null)
        meView.getUserInfo(userInfoBean);
    }
}
