package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.mvp.BasePresenter;

/**
 * Created by yyj on 2018/1/5.
 */

public class ScenicPresenter extends BasePresenter<ScenicContact.IScenicView> implements ScenicContact.IScenicListener {
    private ScenicModel scenicModel;
    private ScenicContact.IScenicView scenicView;
    public ScenicPresenter(ScenicContact.IScenicView view) {
        scenicModel = new ScenicModel();
        attachV(view);
    }

    @Override
    public void attachV(ScenicContact.IScenicView view) {
        super.attachV(view);
        this.scenicView=view;
    }
    public void requestScenicList(double longitude, final double latitude, int passport_type, int city_id, int page){
        scenicModel.requestScenicList( longitude,   latitude,  passport_type,  city_id,  page,this);
    }

    public void requestScenicDetails(String scenic_id){
        scenicModel.requestScenicDetails(scenic_id,this);
    }
    @Override
    public void error(String msg) {
        if(scenicView!=null)
            scenicView.error(msg);
    }

    @Override
    public void scenicList(ScenicListResponse response) {

        if(scenicView!=null)
            scenicView.getScenicList(response);
    }

    @Override
    public void reponseScenicDetails(ScenicDetailResponse response) {
        if(scenicView!=null){
            scenicView.scenicDetails(response);
        }
    }
}