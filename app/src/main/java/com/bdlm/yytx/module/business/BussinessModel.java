package com.bdlm.yytx.module.business;

import com.bdlm.yytx.api.IBussinessApi;
import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 关于商家的 接口请求
 * Created by yyj on 2018/2/2.
 */

public class BussinessModel extends BusinessContact.BaseBussinessModel {

    @Override
    void submitApprove(BusinessBean businessBean, final BusinessContact.IBusinessListener listener) {
        Subscribe(getApiService(IBussinessApi.class).submitApprove(businessBean.getMobile(), businessBean.getShop_name(), businessBean.getManage_type(), businessBean.getLongitude(), businessBean.getLatitude(), businessBean.getAddress(), businessBean.getDiscount_info(), businessBean.getSpecial_explain(), businessBean.getLogo_img()), new IApiReturn<BusinessBean>() {
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
        RequestBody userNameBody = RequestBody.create(MediaType.parse("form-data"), type);
        RequestBody upFile= null;
        try {
            upFile = RequestBody.create(MediaType.parse("application/otcet-stream"),file);
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showToastCust("图片文件为空");
        }
        Subscribe(getApiService(IBussinessApi.class).uploadPic(userNameBody, upFile), new IApiReturn<UploadPicRespon>() {
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

    @Override
    void requestBussinessList(String manage_type, String longitude, String latitude, String page, final BusinessContact.IBusinessListener listener) {
        Subscribe(getApiService(IBussinessApi.class).bussinessList(manage_type, longitude, latitude, page), new IApiReturn<BusinessListResponse>() {
            @Override
            public void run(ApiResultBean<BusinessListResponse> apiResult) {
                if (listener != null) {
                    if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                        listener.bussinessList(apiResult.getData());
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
