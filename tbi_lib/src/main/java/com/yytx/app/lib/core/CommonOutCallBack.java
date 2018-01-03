package com.yytx.app.lib.core;

/**
 * 双返回数据
 * Created by shikeyue on 17/6/15.
 */

public interface CommonOutCallBack<T, V> {

    void onCallBack(T t, V v);

}
