package com.trsoft.app.lib.mvp;

/**
 * Created by yyj on 2017/12/26.
 */

public interface IBasePresenter<V extends IBaseView> {

    void attachV(V view);

    void detachV();
}
