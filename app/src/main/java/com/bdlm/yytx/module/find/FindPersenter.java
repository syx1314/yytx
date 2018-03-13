package com.bdlm.yytx.module.find;

import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TourInfoListBean;
import com.bdlm.yytx.entity.TourInfoListResponse;
import com.trsoft.app.lib.mvp.BasePresenter;

import java.util.List;

/**
 * Created by yyj on 2018/2/7.
 */

public class FindPersenter extends BasePresenter implements IFindContact.IFindListener {

    IFindContact.IFindView findView;
    IFindContact.ITourInfoListView tourInfoListView;
    FindModel findModel;

    public FindPersenter(IFindContact.IFindView findView) {
        this.findView = findView;
        attachV(findView);
        findModel = new FindModel();
    }

    public FindPersenter(IFindContact.ITourInfoListView tourInfoListView) {
        this.tourInfoListView = tourInfoListView;
        attachV(tourInfoListView);
        findModel = new FindModel();
    }

    public void requestRecommendScenic() {
        findModel.requestRecommendScenic(this);
    }

    public void requestSortScenic() {
        findModel.requestSortScenic(this);
    }

    public void requestTourInfoList(String page) {
        findModel.requestTourInfoList(page, this);
    }

    @Override
    public void error(String msg) {
        if (findView != null) {
            findView.error(msg);
        }
    }

    @Override
    public void resultScenic(List<ScenicResponse> responses) {
        if (findView != null) {
            findView.resultScenic(responses);
        }
    }

    @Override
    public void resultSortScenic(List<ScenicPlaySortBean> responses) {
        if (findView != null) {
            findView.resultSortScenic(responses);
        }
    }

    @Override
    public void resultTourInfoList(TourInfoListResponse tourInfoList) {
        if (tourInfoListView != null) {
            tourInfoListView.resultTourInfoList(tourInfoList);
        }
    }
}
