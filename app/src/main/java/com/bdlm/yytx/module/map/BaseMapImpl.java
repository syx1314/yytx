package com.bdlm.yytx.module.map;

import android.os.Bundle;

import com.amap.api.maps.MapView;

/**
 * Created by yyj on 2018/1/5.
 */

public class BaseMapImpl implements IBaseMap {
    @Override
    public   void loadMap(MapView mapView, Bundle savedInstanceState) {
        new GdMap(mapView, savedInstanceState);
    }
}
