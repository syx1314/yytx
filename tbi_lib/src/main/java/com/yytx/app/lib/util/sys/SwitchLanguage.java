package com.yytx.app.lib.util.sys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.yytx.app.lib.BaseApplication;
import com.yytx.app.lib.core.IBaseConst;
import com.yytx.app.lib.view.ActivitySupport;

import java.util.Locale;

/**
 * 切换语言
 * Created by huangzhe on 2017/4/14.
 */

public class SwitchLanguage {
    /**
     * 设置语言
     * @param locale
     * @param activitySupportClass
     */
    public static void setLanguage(ActivitySupport curActivity,Locale locale, Class<?> activitySupportClass){
        PrefManager.saveString(BaseApplication.mContext, IBaseConst.PreManager.LOCAL_LANGUAGE,locale.getLanguage());
        Resources resources = BaseApplication.mContext.getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale;
        resources.updateConfiguration(config, dm);
        if(activitySupportClass!=null&&curActivity!=null) {
            curActivity.finish();//如果不重启当前界面，是不会立马修改的
            curActivity.startActivity(new Intent(curActivity, activitySupportClass));//跳转会首页
        }
    }
}
