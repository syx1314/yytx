package com.bdlm.yytx.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

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

        if (mPresenter != null) {
            mPresenter.attachV(this);

        }
        return mRootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            if (isImmersionBarEnabled()) {
                initImmersionBar();

            }
        }
        createPresenter();
    }

    /**
     * 请求权限
     *
     * @param permission  权限
     * @param okTips      授权提示
     * @param noTips      拒绝权限
     * @param noAgainTips 点击不在提醒 提示用户如何操作
     */
    protected void requestPermissoin(final String okTips, final String noTips, final String noAgainTips, String... permission) {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (mContext == null) {
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.requestEach(permission).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
                    // 用户已经同意该权限
//                    DialogUtil.showAlert(mContext, okTips, null);

                } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

//                    DialogUtil.showAlert(mContext, noTips, null);
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                   DialogUtil.showAlertCusTitlel(mContext,"温馨提示",noAgainTips,"是","否", new CommonCallback<Boolean>() {
                        @Override
                        public void onCallBack(Boolean data) {
                            if(data){
                                Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                startActivity(intent);
                            }
                        }
                    });

                }
            }
        });
    }


    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(mContext);
        mImmersionBar.init();
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
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
