package com.bdlm.yytx.module.map;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.amap.api.services.core.PoiItem;
import com.bdlm.yytx.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchPoiActivity extends GdMapActivity {


    @BindView(R.id.et_cancel)
    TextView etCancel;
    @BindView(R.id.map)
    MapView map;


    @Override
    protected int getLayout() {
        return R.layout.activity_search_poi;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
    }


    @OnClick(R.id.et_cancel)
    public void cancel(View view) {
        finish();
    }

    @OnClick(R.id.tv_search_key)
    public void toSearchArea(View view) {
        Intent intent = new Intent(activity, SearchAreaActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        startActivityForResult(intent, 1000);
    }


}
