package com.trsoft.app.lib.mvp;

import com.trsoft.app.lib.constant.Constant;
import com.trsoft.app.lib.http.ApiException;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.ApiService;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.rx.RxApiManager;


import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yyj
 * @date 2017/12/28
 */

public abstract class BaseModel implements IBaseModel {



    /**
     * 得到API的Service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T getApiService(Class<T> service) {
        return ApiService.getInstance(Constant.BASE_URL).create(service);
    }

    public <T> void Subscribe(final Observable<ApiResultBean<T>> observable, final IApiReturn<T> apiReturn) {
        final int tag = this.hashCode();
        RxApiManager.instance().add(tag, observable.hashCode(), observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ApiResultBean<T>>() {
            @Override
            public void onCompleted() {
                RxApiManager.instance().cancel(tag, observable.hashCode());
            }

            @Override
            public void onError(Throwable e) {
              if(ApiException.handleException(e).message!=null) {
                apiReturn.error(ApiException.handleException(e).message);
              }
                RxApiManager.instance().cancel(tag);
            }
            @Override
            public void onNext(ApiResultBean<T> tApiResult) {
                if(tApiResult!=null) {
                    apiReturn.run(tApiResult);
                }
            }
        }));
    }
}
