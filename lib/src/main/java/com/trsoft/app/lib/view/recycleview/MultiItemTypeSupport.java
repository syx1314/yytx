package com.trsoft.app.lib.view.recycleview;

/**
 * Created by yyj on 2017/12/29.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);

}
