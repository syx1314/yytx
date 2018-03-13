package com.bdlm.yytx.module.home;

import com.bdlm.yytx.entity.HomeURLBean;
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

    //获取当前位置
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
    public void requestScenicList(String longitude, String latitude, String scenic_id) {
        if (longitude != null & latitude != null) {
            model.requestScenicList(longitude, latitude, null, this);
        } else {
            model.requestScenicList(null, null, scenic_id, this);
        }
    }

    public void recommendScenic() {
        model.requestRecommendScenic(this);
    }

    public void requestShopUrl(){
        model.resultTourGoodsUrl(this);
    }
    //获取公告
    public void getNotice() {
      model.getNotice(this);
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

    @Override
    public void notice(String noticeStr) {
        homeView.resultNotice(noticeStr);
    }

    @Override
    public void resultTourGoodsUrl(HomeURLBean urlBean) {
        if(homeView!=null){
            homeView.resultTourGoodsUrl(urlBean);
        }
    }
}
