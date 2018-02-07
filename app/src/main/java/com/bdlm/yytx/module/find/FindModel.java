package com.bdlm.yytx.module.find;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.find.IFindContact.BaseFindModel;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;

import java.util.List;

/**
 * Created by yyj on 2018/2/7.
 */

public class FindModel extends BaseFindModel {
    //获取推荐的景区
    @Override
    void requestRecommendScenic(final IFindContact.IFindListener listener) {
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
}
