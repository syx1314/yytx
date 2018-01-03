package com.trsoft.app.lib.view.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trsoft.app.lib.view.recycleview.MultiItemTypeSupport;
import com.trsoft.app.lib.view.recycleview.ViewHolder;

import java.util.List;

/**
 * 复杂布局Adapter
 *
 * @author yyj
 * @date 2017/12/29
 */

public abstract class MultiItemBaseRecycleViewAdapter<T> extends BaseRecycleViewAdapter<T> {
    private MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemBaseRecycleViewAdapter(List mdatas, int resLayoutId) {
        super(mdatas, resLayoutId);
    }

    public MultiItemBaseRecycleViewAdapter(List<T> mdatas,
                                           MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mdatas, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mdatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        if (parent.getContext() != null) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            return new ViewHolder(inflate);
        }
        return null;
    }
}
