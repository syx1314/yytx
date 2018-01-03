package com.yytx.app.lib.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yytx.app.lib.entity.BaseBeanResult;

/**
 * 系统接口
 * Created by huangzhe on 2017/2/23.
 */

public abstract class BaseSysService extends Service {

    protected CommonCallback<BaseBeanResult> callback;//回调接口

    /**
     * 设置回调函数
     * @param callback
     */
    public void setCallback(CommonCallback<BaseBeanResult> callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SysServiceBinder();
    }

    /**
     * 对外提供的绑定接口
     */
    public class SysServiceBinder extends Binder{
        /**
         * 得到当前Service
         * @return
         */
        public <T extends BaseSysService> T getService(){
            return (T)BaseSysService.this;
        }
    }
}
