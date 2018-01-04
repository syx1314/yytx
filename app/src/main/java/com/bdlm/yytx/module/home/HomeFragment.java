package com.bdlm.yytx.module.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.module.scenic.ScenicListActivity;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页 Fragment
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.tv_cash_coupon)
    TextView tvCashCoupon;
    @BindView(R.id.tv_scenic_spot)
    TextView tvScenicSpot;
    @BindView(R.id.tv_tourist_goods)
    TextView tvTouristGoods;
    @BindView(R.id.tv_chi)
    TextView tvChi;
    @BindView(R.id.tv_live)
    TextView tvLive;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tv_travel_agency)
    TextView tvTravelAgency;
    Unbinder unbinder;

    @Override
    protected void createPresenter() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void error(String msg) {

    }



    @OnClick({R.id.tv_cash_coupon, R.id.tv_scenic_spot, R.id.tv_tourist_goods, R.id.tv_chi, R.id.tv_live, R.id.tv_play, R.id.tv_travel_agency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cash_coupon:
                break;
            case R.id.tv_scenic_spot:
                startActivity(new Intent(mContext, ScenicListActivity.class));
                break;
            case R.id.tv_tourist_goods:
                break;
            case R.id.tv_chi:
                break;
            case R.id.tv_live:
                break;
            case R.id.tv_play:
                break;
            case R.id.tv_travel_agency:
                break;
        }
    }
}
