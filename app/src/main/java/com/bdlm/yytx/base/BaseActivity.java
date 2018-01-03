package com.bdlm.yytx.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.module.login.LoginActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.ActivitySupport;
import com.trsoft.app.lib.R;
import com.trsoft.app.lib.mvp.IBasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;

import butterknife.ButterKnife;

/**
 * Created by yyj on 2017/12/27.
 */

public abstract class BaseActivity<P extends IBasePresenter> extends ActivitySupport implements IBaseView {

    private P persenter;
    protected Activity activity;
    protected String token;

    protected abstract int getLayout();

    protected abstract void createPersenter();

    public void isLogin() {
        if (Validator.isNotEmpty(PreferenceUtils.getInstance().getString(Constant.TOKEN))) {
            token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
            Logger.e(token);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        createPersenter();
//        persenter.attachV(this);
        isLogin();
        activity = this;
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.red)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .init();  //必须调用方可沉浸式
    }

    @Override
    protected void onDestroy() {
        if (persenter != null) {
            persenter.detachV();
        }
        super.onDestroy();
//        if (mImmersionBar != null)
//            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
