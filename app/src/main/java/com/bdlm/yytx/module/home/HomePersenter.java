package com.bdlm.yytx.module.home;

import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.map.GdLocation;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.BasePresenter;

import java.util.List;

/**
 * Created by yyj on 2018/1/12.
 */

public class HomePersenter extends BasePresenter<IHomeContact.IHomeView> implements IHomeContact.IHomeListener {

    private IHomeContact.IHomeView homeView;
    private HomeModel model;

    public HomePersenter(IHomeContact.IHomeView homeView) {
        model = new HomeModel();
        attachV(homeView);
    }

    public void getPosition() {
        GdLocation location = new GdLocation();
        location.setResult(new GdLocation.OnLocationResult() {
            @Override
            public void locationResult(PositionBean position) {

                if (homeView != null)
                    homeView.resultPosition(position);
            }

            @Override
            public void error(Object object) {
                homeView.error(object + "");
            }
        });
        location.startLocation();
    }

    public void recommendScenic() {
        model.requestRecommendScenic(this);
    }

    @Override
    public void attachV(IHomeContact.IHomeView view) {
        super.attachV(view);
        this.homeView = view;
    }

    @Override
    public void error(String msg) {
        if (homeView != null) {
            homeView.error(msg);
        }
    }


    @Override
    public void resultScenic(List<ScenicResponse> responses) {
        if (homeView != null) {
            homeView.resultScenic(responses);
        }
    }
}
