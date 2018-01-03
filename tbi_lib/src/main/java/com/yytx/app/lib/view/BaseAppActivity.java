package com.yytx.app.lib.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yytx.app.lib.util.Validator;
import com.yytx.app.lib.util.ui.SystemBarTintManager;

import org.xutils.x;

import yytx.com.android.lib.R;


/**
 * Activity基类
 * Created by huangzhe on 2016/11/25.
 */

public class BaseAppActivity extends ActivitySupport {
    protected BaseAppActivity ctx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        x.view().inject(this);
    }

    //region 碎片方法
    /**
     * 添加碎片
     *
     * @param fragment
     * @param tag
     */
    public void addFragment(int fragmentId, Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragmentId, fragment, tag);
        transaction.commit();
    }

    /**
     * 添加碎片
     *
     * @param fragmentId
     * @param fragment
     * @param tag
     * @param isHidden   是否隐藏
     */
    protected void addFragment(int fragmentId, Fragment fragment, String tag, boolean isHidden) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragmentId, fragment, tag);
        if (isHidden) {
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    /**
     * 显示Fragment
     *
     * @param tag
     */
    protected void showFragment(String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            transaction.hide(fragment);
        }
        BaseAppFragment baseAppFragment = (BaseAppFragment) getSupportFragmentManager().findFragmentByTag(tag);
        transaction.show(baseAppFragment);
        baseAppFragment.onActivate();//调用激活
        transaction.commit();
    }
    //endregion

    //region 状态栏
    protected void setStatusColor() {
        View root = getRootView(this);
        if (root != null) {
            getRootView(this).setFitsSystemWindows(true);
        }
        setStatusBarStyle(R.color.red1);
    }
    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
    /**
     * 浸染状态栏底色背景，若使用布局的背景图浸染，设置resId为透明色即可
     * 其它情况设置resId为ToolBar(ActionBar)的背景色
     *
     * @param resId 颜色id
     */
    public void setStatusBarStyle(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resId);//通知栏所需颜色

        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(resId);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    //endregion

    //region 退出系统
    private boolean isExit;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    protected boolean keyExit(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quitApp();
            return false;
        }
        return true;
    }

    /**
     * 完成
     *
     * @param keyCode
     * @param event
     * @return
     */
    protected boolean onFinish(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 快速退出系统
     */
    public void quitFastApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
        getBaseApplication().quit(false);
    }

    public void quitApp() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
            getBaseApplication().quit(false);
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };
    //endregion


    //endregion

}