package com.bdlm.yytx.module.business;

import com.bdlm.yytx.api.IBussinessApi;
import com.bdlm.yytx.entity.BusinessBean;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

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
}
