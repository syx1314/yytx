package com.bdlm.yytx.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.gyf.barlibrary.ImmersionBar;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yyj on 2017/12/28.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {


    ;
    private P mPresenter;
    private Unbinder mUnbinder;
    protected Activity mContext;

    protected abstract void createPresenter();

    protected abstract int getLayout();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mRootView = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        createPresenter();
        if (mPresenter != null) {
            mPresenter.attachV(this);

        }

        return mRootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();
        if (mContext != null) {
            ImmersionBar.with(mContext).statusBarColor(R.color.red).init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();

        }
    }
}