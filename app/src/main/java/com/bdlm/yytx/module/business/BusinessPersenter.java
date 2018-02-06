package com.bdlm.yytx.module.business;

import com.bdlm.yytx.entity.BusinessBean;
import com.trsoft.app.lib.mvp.BasePresenter;

/**
 * Created by yyj on 2018/2/2.
 */

public class BusinessPersenter extends BasePresenter implements BusinessContact.IBusinessListener {

    BusinessContact.IBusinessView businessView;


    BussinessModel model;

    public BusinessPersenter(BusinessContact.IBusinessView businessView) {
        this.businessView = businessView;
        attachV(businessView);
        model = new BussinessModel();
    }

    //提交审核
    public void submit(BusinessBean business) {
        model.submitApprove(business, this);
    }

    @Override
    public void error(String msg) {
        if (businessView != null)
            businessView.error(msg);
    }

    @Override
    public void submitApprove(String msg) {
        if (businessView != null)
            businessView.resultApprove(msg);
    }
}



