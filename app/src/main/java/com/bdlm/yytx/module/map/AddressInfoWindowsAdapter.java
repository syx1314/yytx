package com.bdlm.yytx.module.map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.bdlm.yytx.R;
import com.trsoft.app.lib.BaseApplication;

/**
 * Created by yyj on 2018/2/28.
 * 选择地图的poi信息的 infoWindow
 */

public class AddressInfoWindowsAdapter implements AMap.InfoWindowAdapter {
    private OnAddressClickListener onAddressClickListener;

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = LayoutInflater.from(BaseApplication.mContext).inflate(R.layout.view_main_start_tip, null);
        TextView adddTv = view.findViewById(R.id.addr_tv);
        adddTv.setText(marker.getTitle() + marker.getSnippet());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(onAddressClickListener!=null){
                     onAddressClickListener.onAddressClickListener(marker);
                 }
            }
        });
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
    public  interface  OnAddressClickListener{
        void onAddressClickListener(Marker marker);


    }
}
