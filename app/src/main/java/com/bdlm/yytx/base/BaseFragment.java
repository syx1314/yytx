package com.bdlm.yytx.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.module.login.LoginActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;
import com.trsoft.app.lib.utils.PreferenceUtils;

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
    protected ImmersionBar mImmersionBar;
    private String token;

    protected abstract void createPresenter();

    protected abstract int getLayout();

    public void isLogin() {
        token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        Logger.e(token);
        if (TextUtils.isEmpty(token)) {
            if (mContext != null && mContext.getClass() != LoginActivity.class) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
            return;
        }

    }


    /**
     * 跳转指定页面
     */
    public void toActivity(Class activityClass) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), activityClass);
            getActivity().startActivity(intent);
            mContext.finish();
        }

    }

    /**
     * 不清楚当前页面
     *
     * @param activityClass
     */
    public void toActivityNoClear(Class activityClass) {
        if (mContext != null) {
            Intent intent = new Intent(mContext, activityClass);
            startActivity(intent);
        }

    }

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
        mContext = getActivity();
        if (mContext != null) {
//            ImmersionBar.with(mContext).statusBarColor(R.color.red).init();
            if (isImmersionBarEnabled()) {
                initImmersionBar();

            }
        }
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
}
