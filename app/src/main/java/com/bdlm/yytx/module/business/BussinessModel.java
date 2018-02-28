package com.bdlm.yytx.module.business;

import com.bdlm.yytx.api.IBussinessApi;
import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

import java.io.File;
import java.util.List;

/**
 * 关于商家的 接口请求
 * Created by yyj on 2018/2/2.
 */

public class BussinessModel extends BusinessContact.BaseBussinessModel {

    @Override
    void submitApprove(BusinessBean businessBean, final BusinessContact.IBusinessListener listener) {
        Subscribe(getApiService(IBussinessApi.class).submitApprove(businessBean.getMobile(), businessBean.getShop_name(), businessBean.getManage_type(), businessBean.getLongitude(), businessBean.getLatitude(), businessBean.getAddress(), businessBean.getDiscount_info(), businessBean.getDescribe(), businessBean.getLogo_img()), new IApiReturn<BusinessBean>() {
            @Override
            public void run(ApiResultBean<BusinessBean> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                        listener.submitApprove(apiResult.getMsg());
                    } else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if (listener != null) {
                    listener.error(message);
                }

            }
        });
    }

    @Override
    void requestManagerType(final BusinessContact.IBusinessListener listener) {
        Subscribe(getApiService(IBussinessApi.class).getManagerType(), new IApiReturn<List<ManagerTypeBean>>() {
            @Override
            public void run(ApiResultBean<List<ManagerTypeBean>> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                        listener.managerType(apiResult.getData());
                    } else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if (listener != null) {
                    listener.error(message);
                }
            }
        });
    }

    @Override
    void uploadFile(String type, File file, final BusinessContact.IBusinessListener listener) {
        Subscribe(getApiService(IBussinessApi.class).uploadPic(type, file), new IApiReturn<UploadPicRespon>() {
            @Override
            public void run(ApiResultBean<UploadPicRespon> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                        listener.uploadFile(apiResult.getData());
                    } else {
                        listener.error(apiResult.getMsg());
                    }
                }
            }

            @Override
            public void error(String message) {
                if (listener != null) {
                    listener.error(message);
                }
            }
        });
    }
}
