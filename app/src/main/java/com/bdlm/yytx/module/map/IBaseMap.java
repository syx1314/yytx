package com.bdlm.yytx.module.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.MapView;
import com.bdlm.yytx.base.BaseActivity;

/**
 * Created by yyj on 2018/1/5.
 */

public interface IBaseMap {

     void loadMap(MapView mapView, Bundle savedInstanceState);
}
