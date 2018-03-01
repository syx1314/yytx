package com.bdlm.yytx.module.business;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.mvp.BasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yyj on 2018/2/2.
 */

public class BusinessPersenter extends BasePresenter implements BusinessContact.IBusinessListener {


    BusinessContact.IBusinessView businessView;
    BusinessContact.IBussinessListView bussinessListView;
    BussinessModel model;

    public BusinessPersenter(BusinessContact.IBusinessView businessView) {
        this.businessView = businessView;
        attachV(businessView);
        model = new BussinessModel();
    }
    public BusinessPersenter(BusinessContact.IBussinessListView bussinessListView) {
        this.bussinessListView = bussinessListView;
        attachV(bussinessListView);
        model = new BussinessModel();
    }

    //提交审核
    public void submit(BusinessBean business) {
        model.submitApprove(business, this);
    }

    public void requestManagerType() {
        model.requestManagerType(this);
    }

    public void requestBussinessList(String manage_type,String longitude,String latitude,String page){
        model.requestBussinessList(manage_type,longitude,latitude,page,this);
    }
    public void uploadFile(String type,File file){
        model.uploadFile(type,file,this);
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

    @Override
    public void managerType(List<ManagerTypeBean> managerTypeBeanList) {
        if (businessView != null) {
            businessView.resultManagerType(managerTypeBeanList);
        }
    }

    @Override
    public void uploadFile(UploadPicRespon respon) {
        if(businessView!=null){
            businessView.reultUploadFile(respon);
        }
    }

    @Override
    public void bussinessList(BusinessListResponse response) {
     if(bussinessListView!=null){
         bussinessListView.resultBussinessList(response);
     }
    }


}



