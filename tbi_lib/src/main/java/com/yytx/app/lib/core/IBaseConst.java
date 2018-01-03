package com.yytx.app.lib.core;

/**
 * Created by huangzhe on 2016/11/29.
 */

public interface IBaseConst {

    /**
     * 应用自己用到的系统常量
     */
    interface SYS {
        String LOG_TAG = "TBI_TAG";//日志分类
        boolean isWrittenToSD = false;//日志是否可以写到SD卡中
        boolean isTestMode = true;//当前是否为调试模式
    }

    /**
     * 基础常量
     */
    interface BASE {
        int DIALOG_AUTO_CLOSE_TIME=60 * 1000;//自动关闭屏蔽层的时间
    }

    /**
     * 本地数据保存
     */
    interface PreManager {
        String LOCATION_LAT = "locationLat";    //本地定位坐标
        String LOCATION_LOG = "locationLog";    //本地定位坐标

        String LOCAL_LANGUAGE="localLanguage";//当前语言
    }

}
