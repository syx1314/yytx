package com.bdlm.yytx.module.home;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.city.SelCityActivity;
import com.bdlm.yytx.module.scenic.ScenicListActivity;
import com.bdlm.yytx.module.scenic.SearchScenicActivity;
import com.bdlm.yytx.module.scenic.TicketListActivity;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.DisplayUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.iv_scenic1)
    ImageView ivScenic1;
    @BindView(R.id.iv_scenic2)
    ImageView ivScenic2;
    @BindView(R.id.tv_scenic2)
    TextView tvScenic2;
    @BindView(R.id.rv)
    RecyclerView rv;
    private boolean isFrist = true;
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
        requestPermissoin("权限获得", "没有获得权限", "您禁止了权限,可能导致某些功能无法使用是否去开启", needPermissions);
        persenter = new HomePersenter(this);
        persenter.getPosition();
        persenter.recommendScenic();
        title.setClickFun(new CommonTitle.IClickFun() {
            @Override
            public void leftOclick() {

            }

            @Override
            public void rightOclick() {
                toActivityNoClear(SearchScenicActivity.class);
            }
        });
        rv.setLayoutManager(new GridLayoutManager(mContext, 2));
    }

    @Override
    public void onStart() {
        super.onStart();
        mImmersionBar.statusBarColor(R.color.color_status_bar).fitsSystemWindows(true).init();
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
                toActivityNoClear(TicketListActivity.class);
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
    public void resultScenic(List<ScenicResponse> responses) {
        final List<ScenicResponse> tempScenic = new ArrayList<>();
        if (responses != null && responses.size() > 1) {
            ImageLoader.display(responses.get(0).getThumbnail(), ivScenic1);
            ImageLoader.display(responses.get(1).getThumbnail(), ivScenic2);
            ValidatorUtil.setTextVal(tvScenic2, responses.get(1).getName());
        }
        tempScenic.addAll(responses.subList(2, responses.size()));
        rv.setAdapter(new BaseRecycleViewAdapter<ScenicResponse>(tempScenic, R.layout.item_main_scenic) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ScenicResponse scenicResponse = tempScenic.get(position);
                holder.setImage(R.id.iv_scenic, scenicResponse.getThumbnail());
                holder.setText(R.id.tv_scenic_name, scenicResponse.getName());
                holder.setText(R.id.tv_zc, scenicResponse.getPassport_type_name());

            }
        });
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(mContext, msg, null);
    }


}
