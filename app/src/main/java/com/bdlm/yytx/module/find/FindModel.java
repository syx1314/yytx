package com.bdlm.yytx.module.find;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TourInfoListBean;
import com.bdlm.yytx.entity.TourInfoListResponse;
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
    protected void requestRecommendScenic(final IFindContact.IFindListener listener) {
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

    //游玩排行景区
    @Override
    protected void requestSortScenic(final IFindContact.IFindListener listener) {
        Subscribe(getApiService(IScenicApi.class).scenicPlaySort(), new IApiReturn<List<ScenicPlaySortBean>>() {
            @Override
            public void run(ApiResultBean<List<ScenicPlaySortBean>> apiResult) {
                if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                    if (listener != null)
                        listener.resultSortScenic(apiResult.getData());
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
    protected void requestTourInfoList(String page, final IFindContact.IFindListener listener) {
     Subscribe(getApiService(IScenicApi.class).getTourInfoList(page), new IApiReturn<TourInfoListResponse>() {
         @Override
         public void run(ApiResultBean<TourInfoListResponse> apiResult) {
             if (isSuccess(apiResult.getCode()) && apiResult.getData() != null) {
                 if (listener != null)
                     listener.resultTourInfoList(apiResult.getData());
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
