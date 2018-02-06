package com.bdlm.yytx.module.home;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.api.ISysApi;
import com.bdlm.yytx.entity.ScenicResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.mvp.BaseModel;

import java.util.List;

/**
 * Created by Adim on 2018/1/21.
 */

public class HomeModel extends BaseModel {
    public void requestRecommendScenic(final IHomeContact.IHomeListener listener) {
        Subscribe(getApiService(IScenicApi.class).recommend(), new IApiReturn<List<ScenicResponse>>() {
            @Override
            public void run(ApiResultBean<List<ScenicResponse>> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.resultScenic(apiResult.getData());
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

    public void getNotice(final IHomeContact.IHomeListener listener) {
        Subscribe(getApiService(ISysApi.class).systemNotice("SYSTEMNOTICE"), new IApiReturn<String>() {
            @Override
            public void run(ApiResultBean<String> apiResult) {

                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.notice(apiResult.getData());
                } else {
                    if (listener != null) {
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
