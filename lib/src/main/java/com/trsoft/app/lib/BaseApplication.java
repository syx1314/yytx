package com.trsoft.app.lib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.entity.ILoginConfig;
import com.trsoft.app.lib.http.ApiService;

import java.util.LinkedList;
import java.util.List;

/**
 * 全局Application的基类
 * Created by huangzhe on 2016/11/25.
 */

public abstract class BaseApplication extends Application{
    //region 变量

    public static Context mContext;

    /**
     * 得到API的Service
     * @param service
     * @param <T>
     * @return
     */
    public <T> T getApiService(Class<T> service){
        return ApiService.getInstance(getApiUrl()).create(service);
    }
    //endregion

    //region 待实现
    /**
     * 得到API接口地址
     * @return
     */
    public abstract String getApiUrl();

    /**
     * 退出程序
     * @param isClearData
     */
    public abstract void quit(boolean isClearData);


    /**
     * 得到登录页面类用于跳转
     * @return
     */
    public abstract Class getLoginActivityClass();

    /**
     * 保存登录状态
     * @param mLoginConfig
     */
    public abstract void saveLoginConfig(ILoginConfig mLoginConfig);

    /**
     * 得到当前登录用户
     * @return
     */
    public abstract ILoginConfig getLoginConfig();
    //endregion

    //region 初始化
    @Override
    public void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    //endregion

    //region Activity集合
    protected List<Activity> activityList = new LinkedList<Activity>();
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    public void popupActivity(Activity activity) {
        activityList.remove(activity);
    }

    //endregion



}
