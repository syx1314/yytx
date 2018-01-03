package com.yytx.app.lib.core;

/**
 * 通用接口
 * 适用于方法传参，回调方法时
 * Created by huangzhe on 2016/11/25.
 */

public interface CommonCallback<T> {
    void onCallBack(T data);
}
