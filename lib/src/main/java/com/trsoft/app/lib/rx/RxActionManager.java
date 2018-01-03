package com.trsoft.app.lib.rx;

import rx.Subscription;

/**
 * Rx管理器
 * Created by huangzhe on 2017/4/17.
 */

public interface RxActionManager<T> {
    /**
     * 向容器中添加
     * @param tag 标签
     * @param subscription
     */
    void add(T tag, Object id, Subscription subscription);

    /**
     * 删除
     * @param tag
     */
    void remove(T tag, Object id);

    /**
     * 取消
     * @param tag
     */
    void cancel(T tag);

    void cancel(T tag, Object id);
}
