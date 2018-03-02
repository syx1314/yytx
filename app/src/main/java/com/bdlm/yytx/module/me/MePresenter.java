package com.bdlm.yytx.module.me;

import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;
import com.trsoft.app.lib.utils.MyLog;

/**
 * Created by yyj on 2018/1/4.
 */

public class MePresenter extends BasePresenter implements MeContact.IMeListener {

    MeContact.IMeView meView;
    MeContact.IFeedBackView feedBackView;
    MeModel meModel;

    public MePresenter(MeContact.IMeView view) {
        this.meModel = new MeModel();
        meView=view;
        attachV(view);
    }

    public MePresenter(MeContact.IFeedBackView feedBackView) {
        this.meModel = new MeModel();
        this.feedBackView = feedBackView;
        attachV(feedBackView);
    }


    public void userInfo() {
        meModel.getUserInfo(this);
    }

    public void submitFeedBack(String mobile, String content) {
        meModel.submitFeedBack(mobile, content, this);
    }

    public void requestLogout() {
        meModel.logout(this);
    }

    @Override
    public void error(String msg) {
        if (meView != null)
            meView.error(msg);
    }

    @Override
    public void userInfo(UserInfoBean userInfoBean) {
        if (meView != null)
            meView.getUserInfo(userInfoBean);
    }

    @Override
    public void logoutResult(String msg) {
        if (meView != null)
            meView.responseLogout(msg);
    }


    @Override
    public void feedBack(String msg) {
        MyLog.e(msg+"");
        if (feedBackView != null) {
            feedBackView.resultFeedBack(msg);
        }
    }
}
