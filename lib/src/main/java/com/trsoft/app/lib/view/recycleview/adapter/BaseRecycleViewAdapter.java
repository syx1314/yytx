package com.trsoft.app.lib.view.recycleview.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trsoft.app.lib.view.recycleview.ViewHolder;

import java.util.List;

/**
 * Created by yyj on 2017/12/29.
 */

public class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public List<T> mdatas;
    private int resLayoutId;

    public BaseRecycleViewAdapter(List<T> mdatas,@LayoutRes int resLayoutId) {
        this.mdatas = mdatas;
        this.resLayoutId=resLayoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent.getContext()!=null) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(resLayoutId, null);
           return new ViewHolder(inflate);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          if(holder==null){
              return;
          }
    }

    @Override
    public int getItemCount() {
        return mdatas != null ? mdatas.size() : 5;
    }
}
