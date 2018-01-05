package com.bdlm.yytx.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.module.login.LoginActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trsoft.app.lib.ActivitySupport;
import com.trsoft.app.lib.R;
import com.trsoft.app.lib.mvp.IBasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;

import butterknife.ButterKnife;
import rx.Observer;
import rx.functions.Action1;


/**
 * Created by yyj on 2017/12/27.
 */

public abstract class BaseActivity<P extends IBasePresenter, V extends IBaseView> extends ActivitySupport {

    private P persenter;
    protected Activity activity;
    protected String token;
    private V view;

    protected abstract int getLayout();

    protected abstract void createPersenter();

    public void isLogin() {
        token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        Logger.e(token);
        if (TextUtils.isEmpty(token)) {
            if (activity != null && activity.getClass() != LoginActivity.class) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        createPersenter();
        activity = this;
        isLogin();
        ButterKnife.bind(this);
          ImmersionBar.with(this)
                .statusBarColor(R.color.red)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .init();  //必须调用方可沉浸式


    }

    /**
     * 请求权限
     * @param permission  权限
     * @param okTips    授权提示
     * @param noTips    拒绝权限
     * @param noAgainTips  点击不在提醒 提示用户如何操作
     */
    protected  void  requestPermissoin(String permission,String okTips,String noTips,String noAgainTips){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
                    // 用户已经同意该权限
                    DialogUtil.showAlert(activity,"用户已经同意该权限",null);

                } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                    DialogUtil.showAlert(activity,"用户拒绝了该权限",null);
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                    DialogUtil.showAlert(activity,"权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用",null);

                }
            }
        });
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
