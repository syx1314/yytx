package com.bdlm.yytx.module.home;

import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.module.map.GdLocation;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.mvp.BasePresenter;

/**
 * Created by yyj on 2018/1/12.
 */

public class HomePersenter extends BasePresenter<IHomeContact.IHomeView> {

    private IHomeContact.IHomeView homeView;

    public HomePersenter(IHomeContact.IHomeView homeView) {
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

    @Override
    public void attachV(IHomeContact.IHomeView view) {
        super.attachV(view);
        this.homeView = view;
    }
}
