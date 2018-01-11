package com.trsoft.app.lib.mvp;

/**
 * @author yyj
 * @date 2017/12/27
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V>{
    private V mvpView;

    protected V getMvpView() {
        return mvpView;
    }

    @Override
    public void attachV(V view) {
        mvpView=view;
    }

    @Override
    public void detachV() {
        mvpView=null;
    }
}
