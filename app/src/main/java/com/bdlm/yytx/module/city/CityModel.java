package com.bdlm.yytx.module.city;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.CityBean;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

import java.util.List;

/**
 * Created by yyj on 2018/1/10.
 */

public class CityModel extends ISelCityContact.ICityModel {
    @Override
    void requestCityData(final ISelCityContact.ICityListener listener) {
        Subscribe(getApiService(IScenicApi.class).getCitys(), new IApiReturn<List<CityBean>>() {
            @Override
            public void run(ApiResultBean<List<CityBean>> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null) {
                        listener.cityData(apiResult.getData());
                    }
                } else {
                    listener.error(apiResult.getMsg());
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
