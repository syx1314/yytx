package com.trsoft.app.lib.mvp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yyj on 2017/12/28.
 */

public abstract class  BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {


    private Object mUnbinder;
    private P mPresenter;

    protected abstract void createPresenter();

    protected abstract int getLayout();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mRootView = inflater.inflate(getLayout(), container, false);
//        mUnbinder = ButterKnife.bind(this, mRootView);
        createPresenter();
        if (mPresenter!= null) {
            mPresenter.attachV(this);

        }

        return mRootView;

    }
}
