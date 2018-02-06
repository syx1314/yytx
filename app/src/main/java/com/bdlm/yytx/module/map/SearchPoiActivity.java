package com.bdlm.yytx.module.map;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;

import butterknife.BindView;

public class SearchPoiActivity extends BaseActivity {

    @BindView(R.id.et_search_key)
    EditText etSearchKey;
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

        etSearchKey.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
//                    presenter.requestScenicSearch(etSearchKey.getText().toString());
                }
                return false;
            }
        });
    }

}
