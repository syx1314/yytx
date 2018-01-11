package com.bdlm.yytx.module.city;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.entity.CityBean;
import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.module.map.GdLocation;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelCityActivity extends BaseActivity implements ISelCityContact.ICityView {
    SelCityPresenter selCityPresenter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    private GdLocation gdLocation;


    @Override
    protected int getLayout() {
        return R.layout.activity_sel_city;
    }

    @Override
    protected void createPersenter() {
        selCityPresenter = new SelCityPresenter(this);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
        gdLocation = new GdLocation();
        selCityPresenter.requestCityData();
        mImmersionBar.statusBarColor(R.color.red).init();
        gdLocation.setResult(new GdLocation.OnLocationResult() {
            @Override
            public void locationResult(PositionBean position) {
                tvPosition.setText(position.getProvince());

            }

            @Override
            public void error(Object object) {
                 tvPosition.setText(object.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gdLocation.startLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位
        gdLocation.destroyLocation();
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void responseCityData(final List<CityBean> cityBeans) {
        if (cityBeans != null) {
            rv.setAdapter(new BaseRecycleViewAdapter<CityBean>(cityBeans, android.R.layout.simple_list_item_1) {
                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setText(android.R.id.text1, cityBeans.get(position).getName());
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
