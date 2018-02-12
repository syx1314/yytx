package com.bdlm.yytx;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.MapView;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.module.map.BaseMapImpl;


import butterknife.BindView;

public class MapActivity extends BaseLoginActivity {


    @BindView(R.id.map)
    MapView mapView;

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void createPersenter() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        new BaseMapImpl().loadMap(mapView, savedInstanceState);

    }

}
