package com.bdlm.yytx.module.find;

import com.bdlm.yytx.entity.ScenicResponse;
import com.trsoft.app.lib.mvp.BasePresenter;

import java.util.List;

/**
 * Created by yyj on 2018/2/7.
 */

public class FindPersenter extends BasePresenter<IFindContact.IFindView> implements IFindContact.IFindListener {

    IFindContact.IFindView findView;
    FindModel findModel;

    public FindPersenter(IFindContact.IFindView findView) {
        this.findView = findView;
        attachV(findView);
        findModel = new FindModel();
    }

    public void requestRecommendScenic() {
        findModel.requestRecommendScenic(this);
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
}
