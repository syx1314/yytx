package com.bdlm.yytx.module.welcome;

import android.support.annotation.NonNull;

import com.bdlm.yytx.api.ISysApi;
import com.bdlm.yytx.entity.AppVersion;
import com.bdlm.yytx.entity.ImageBean;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.mvp.BaseModel;

import java.util.List;

/**
 * Created by Adim on 2018/1/22.
 */

public class WelcomeModel extends BaseModel {

    private WelResultListener listener;

    public void setListener(WelResultListener listener) {
        this.listener = listener;
    }

    public void checkAppVersion(@NonNull String version) {
        Subscribe(getApiService(ISysApi.class).checkAppVersion(version), new IApiReturn<AppVersion>() {
            @Override
            public void run(ApiResultBean<AppVersion> apiResult) {

                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null) {
                        listener.appInfo(apiResult.getData());
                    }
                }
            }

            @Override
            public void error(String message) {
              listener.error(message);
            }
        });
    }

    public void  getAdvList(String  position_id){
        Subscribe(getApiService(ISysApi.class).adList(position_id), new IApiReturn<List<ImageBean>>() {
            @Override
            public void run(ApiResultBean<List<ImageBean>> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null) {
                        listener.adList(apiResult.getData());
                    }
                }
            }

            @Override
            public void error(String message) {
                listener.error(message);
            }
        });
    }
    public interface WelResultListener {
        void appInfo(AppVersion appVersion);
        void adList(List<ImageBean> beanList);
        void error(String msg);
    }
}
