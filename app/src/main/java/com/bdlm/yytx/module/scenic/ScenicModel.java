package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.api.IPassportApi;
import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

import java.util.List;

/**
 * Created by yyj on 2018/1/4.
 */

public class ScenicModel extends ScenicContact.IScenicModel {
    @Override
    void requestScenicList(String longitude, final String latitude, int passport_type, int city_id, int page, final ScenicContact.IScenicListener listener) {
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

    @Override
    void requestScenicDetails(String scenic_id, final ScenicContact.IScenicListener listener) {
        Subscribe(getApiService(IScenicApi.class).scenicDetails(scenic_id), new IApiReturn<ScenicDetailResponse>() {
            @Override
            public void run(ApiResultBean<ScenicDetailResponse> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.reponseScenicDetails(apiResult.getData());
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

    @Override
    void requestPassportType(final ScenicContact.IScenicListener listener) {
        Subscribe(getApiService(IPassportApi.class).getPassportType(), new IApiReturn<List<PassportTypeBean>>() {
            @Override
            public void run(ApiResultBean<List<PassportTypeBean>> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.responsePassportType(apiResult.getData());
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

    @Override
    void requestSearchScenic(String search_name, final ScenicContact.IScenicListener listener) {
        Subscribe(getApiService(IScenicApi.class).searchScenic(search_name), new IApiReturn<ScenicListResponse>() {
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
