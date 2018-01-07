package com.trsoft.app.lib.view.recycleview;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.labelview.LabelView;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.Validator;

/**
 * Created by yyj on 2017/12/29.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null && Validator.isNotEmpty(text)) {
            tv.setText(text);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(resId);
        }
        return this;
    }
    public ViewHolder setImage(int viewId, String url) {
        ImageView view = getView(viewId);
        if (view != null) {
            ImageLoader.display(url,view);
        }
        return this;
    }

    public ViewHolder setLableText(int viewId,String text){
        LabelView labelView=getView(viewId);
        if(text==null){
            labelView.setVisibility(View.GONE);
        }else {
            labelView.setVisibility(View.VISIBLE);
            labelView.setText(text);
        }
        return this;
    }
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


}
