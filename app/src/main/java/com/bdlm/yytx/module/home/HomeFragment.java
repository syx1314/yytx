package com.bdlm.yytx.module.home;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.module.city.SelCityActivity;
import com.bdlm.yytx.module.map.GdLocation;
import com.bdlm.yytx.module.scenic.ScenicListActivity;
import com.bdlm.yytx.module.scenic.SearchScenicActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements IHomeContact.IHomeView {

    @BindView(R.id.title)
    CommonTitle title;
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
    HomePersenter persenter;
    /**
     * 高德定位需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void createPresenter() {
        requestPermissoin("权限获得", "没有获得权限", "不在提醒", needPermissions);
        persenter = new HomePersenter(this);
        persenter.getPosition();
        title.setClickFun(new CommonTitle.IClickFun() {
            @Override
            public void leftOclick() {

            }

            @Override
            public void rightOclick() {
                toActivityNoClear(SearchScenicActivity.class);
            }
        });
//        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
        mImmersionBar.titleBar(title).init();

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    @OnClick({R.id.title, R.id.tv_cash_coupon, R.id.tv_scenic_spot, R.id.tv_tourist_goods, R.id.tv_chi, R.id.tv_live, R.id.tv_play, R.id.tv_travel_agency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
                toActivityNoClear(SelCityActivity.class);
                break;

            case R.id.tv_cash_coupon:
                break;
            case R.id.tv_scenic_spot:
                toActivityNoClear(ScenicListActivity.class);
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

    @Override
    public void resultPosition(PositionBean positionBean) {
        title.setTvTitle(positionBean.getProvince());
        if (positionBean != null) {
            PreferenceUtils.getInstance().saveData(Constant.CURLAN, positionBean.getLatitude() + "");
            PreferenceUtils.getInstance().saveData(Constant.CURLON, positionBean.getLongitude() + "");
        }
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(mContext, msg, null);
    }

}
