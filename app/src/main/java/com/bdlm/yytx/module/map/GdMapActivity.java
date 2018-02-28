package com.bdlm.yytx.module.map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.module.business.BusinessJoinActivity;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.inter.CommonCallback;

import butterknife.BindView;

/**
 * 高德地图封装类
 * Created by yyj on 2018/1/5.
 * SDK在Android 6.0下需要进行运行检测的权限如下：
 * Manifest.permission.ACCESS_COARSE_LOCATION,
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.READ_PHONE_STATE
 */

public abstract class GdMapActivity extends BaseActivity {
    @BindView(R.id.map)
    MapView mMapView;
    private AMap aMap;
    protected String lat;
    protected String lon;
    private OnLocationlistener locationlistener;
    private AddressInfoWindowsAdapter addressInfoWindowsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap(savedInstanceState);
        initEvent();
    }


    /**
     * 初始化高德地图
     */
    protected void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(Constant.DEFADLT_MAP_ZOOM));
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);

        //设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(final Location location) {
                Logger.e("初始位置信息1111" + location.toString());
                lat = location.getLatitude() + "";
                lon = location.getLongitude() + "";
                //逆地址编码出来地址
                RegeocodeTask task = new RegeocodeTask();
                task.search(location.getLatitude(), location.getLongitude());
                task.setOnLocationGetListener(new CommonCallback<PositionEntity>() {
                    @Override
                    public void onCallBack(PositionEntity data) {
                        Logger.e("逆地址编码" + data);
                        data.latitue = location.getLatitude();
                        data.longitude = location.getLongitude();
                        addMarker(data);
                    }
                });
                if (locationlistener != null) {
                    locationlistener.position(location);

                }
            }
        });
    }

    private void initEvent() {
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                return false;
            }
        });
    }

    //添加一个覆盖物
    private void addMarker(PositionEntity entity) {
        LatLng latLng = new LatLng(entity.latitue, entity.longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(entity.city).snippet(entity.address);
//        markerOption.snippet(entity.address);
        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_poi_position)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        addressInfoWindowsAdapter = new AddressInfoWindowsAdapter();
        aMap.setInfoWindowAdapter(addressInfoWindowsAdapter);
        aMap.addMarker(markerOption).showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constant.DEFADLT_MAP_ZOOM));
        addressInfoWindowsAdapter.setOnAddressClickListener(new AddressInfoWindowsAdapter.OnAddressClickListener() {
            @Override
            public void onAddressClickListener(Marker marker) {
                Intent intent = new Intent(activity, BusinessJoinActivity.class);
                PositionEntity entity = new PositionEntity();
                entity.city = marker.getTitle();
                entity.longitude = marker.getPosition().longitude;
                entity.latitue = marker.getPosition().latitude;
                entity.address = marker.getSnippet();
                intent.putExtra("address", entity);
                setResult(1000, intent);
                finish();
            }
        });
    }

    protected void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        super.onResume();
        mMapView.onResume();
    }

    protected void onPause() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        super.onPause();
        mMapView.onPause();
    }

    protected void onDestroy() {

        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        super.onDestroy();
        mMapView.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        super.onSaveInstanceState(outState);
        if (mMapView != null && outState != null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000 && data != null) {
            PoiItem poi = data.getParcelableExtra("poi");
            if (poi != null) {
                PositionEntity entity = new PositionEntity(poi.getLatLonPoint().getLatitude(), poi.getLatLonPoint().getLongitude(), poi.getSnippet() + poi.getTitle(), poi.getCityName());
                aMap.clear();
                addMarker(entity);
            }
        }

    }

    public interface OnLocationlistener {
        void position(Location location);
    }

}
