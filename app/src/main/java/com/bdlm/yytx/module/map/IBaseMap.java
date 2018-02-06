package com.bdlm.yytx.module.map;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.MapView;

/**
 * Created by yyj on 2018/1/5.
 */

public interface IBaseMap {

     void loadMap(MapView mapView, Bundle savedInstanceState);

     Location getCurPisition();
}
