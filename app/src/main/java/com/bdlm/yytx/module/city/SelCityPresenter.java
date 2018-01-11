package com.bdlm.yytx.module.city;

import com.bdlm.yytx.entity.CityBean;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/1/10.
 */

public class SelCityPresenter extends BasePresenter<ISelCityContact.ICityView> implements ISelCityContact.ICityListener {
    ISelCityContact.ICityView cityView;
    CityModel cityModel;

    public SelCityPresenter(ISelCityContact.ICityView cityView) {
        cityModel = new CityModel();
        attachV(cityView);
    }

    @Override
    public void attachV(ISelCityContact.ICityView view) {
        super.attachV(view);
        cityView = view;
    }

    public void requestCityData() {
        cityModel.requestCityData(this);
    }

    @Override
    public void error(String msg) {
        if (cityView != null) {
            cityView.error(msg);
        }
    }

    @Override
    public void cityData(List<CityBean> cityBeans) {
        if (cityView != null) {
            cityView.responseCityData(cityBeans);
        }
    }
}
