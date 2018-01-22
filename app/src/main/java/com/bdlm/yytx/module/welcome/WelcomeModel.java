package com.bdlm.yytx.module.welcome;

import android.support.annotation.NonNull;

import com.bdlm.yytx.api.ISysApi;
import com.bdlm.yytx.entity.AppVersion;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.mvp.BaseModel;

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

    public interface WelResultListener {
        void appInfo(AppVersion appVersion);

        void error(String msg);
    }
}
