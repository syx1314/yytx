package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBasePresenter;

/**
 * Created by Adim on 2018/1/4.
 */

public  class ScenicPresenter extends BasePresenter<ScenicContact.IScenicView> implements ScenicContact.IScenicListener {

    ScenicContact.IScenicView scenicView;
    ScenicModel scenicModel;

    public ScenicPresenter(ScenicContact.IScenicView view) {
        scenicModel = new ScenicModel();
        attachV(view);
    }

    @Override
    public void attachV(ScenicContact.IScenicView view) {
        this.scenicView = view;
    }


    public void requestScenicList(double longitude, final double latitude, int passport_type, int city_id, int page) {
        scenicModel.requestScenicList(longitude, latitude, passport_type, city_id, page, this);
    }

    @Override
    public void error(String msg) {
        if (scenicView != null) {
            scenicView.error(msg);
        }
    }

    @Override
    public void scenicList(ScenicListResponse response) {
        if (scenicView != null) {
            scenicView.getScenicList(response);
        }
    }
}
