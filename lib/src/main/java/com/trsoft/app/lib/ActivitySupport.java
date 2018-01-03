package com.trsoft.app.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.trsoft.app.lib.entity.ILoginConfig;


/**
 * 管理Activity 加到集合
 * Activity工具类
 */

public class ActivitySupport extends AppCompatActivity {
    //region 变量
    protected BaseApplication mBaseApplication = null;

    public ILoginConfig getLoginConfig() {
        return getBaseApplication().getLoginConfig();
    }


    public BaseApplication getBaseApplication() {
        return mBaseApplication;
    }
    //endregion

    //region 实现方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseApplication = (BaseApplication) getApplication();
        mBaseApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        mBaseApplication.popupActivity(this);
        super.onDestroy();
    }
    //endregion

    //region 工具方法
    /**
     * 关闭键盘事件
     *
     * @author shimiso
     * @update 2012-7-4 下午2:34:34
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开键盘事件
     */
    public void openInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.SHOW_FORCED);
        }
    }
    //endregion
}
