package com.bdlm.yytx;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.MapView;
import com.bdlm.yytx.base.BaseLoginActivity;


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



}
