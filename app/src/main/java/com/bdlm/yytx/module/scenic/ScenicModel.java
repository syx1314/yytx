package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

/**
 * Created by yyj on 2018/1/4.
 */

public class ScenicModel extends ScenicContact.IScenicModel {
    @Override
    void requestScenicList(double longitude, final double latitude, int passport_type, int city_id, int page, final ScenicContact.IScenicListener listener) {
        Subscribe(getApiService(IScenicApi.class).getScenicList(longitude, latitude, passport_type, city_id, page), new IApiReturn<ScenicListResponse>() {
            @Override
            public void run(ApiResultBean<ScenicListResponse> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.scenicList(apiResult.getData());
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
