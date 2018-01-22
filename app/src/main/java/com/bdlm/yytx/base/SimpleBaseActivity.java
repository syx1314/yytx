package com.bdlm.yytx.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.module.login.LoginActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.ActivitySupport;
import com.trsoft.app.lib.utils.PreferenceUtils;

import butterknife.ButterKnife;

/**
 * Created by Adim on 2018/1/22.
 */

public abstract class SimpleBaseActivity extends ActivitySupport {

    protected Activity activity;
    protected ImmersionBar mImmersionBar;

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        activity = this;
        ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    //初始化沉浸式
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(activity);
        mImmersionBar.keyboardEnable(true).init();

    }

    /**
     * 跳转指定页面
     */
    public void toActivity(Class activityClass) {
        if (activity != null) {
            Intent intent = new Intent(activity, activityClass);
            startActivity(intent);
            finish();
        }

    }

    public void toActivityOneDataNoClear(Class activityClass, String data) {
        if (activity != null) {
            Intent intent = new Intent(activity, activityClass);
            intent.putExtra(Constant.BUNDLE_STRING, data);
            startActivity(intent);
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


    protected void toUrl(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
