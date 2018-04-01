package com.bdlm.yytx.module.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.constant.BussinessTypeEnum;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.AppVersion;
import com.bdlm.yytx.entity.HomeURLBean;
import com.bdlm.yytx.entity.ImageBean;
import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.business.BusinessActivity;
import com.bdlm.yytx.module.city.SelCityLoginActivity;
import com.bdlm.yytx.module.scenic.ScenicDetailsActivity;
import com.bdlm.yytx.module.scenic.ScenicListActivity;
import com.bdlm.yytx.module.scenic.SearchScenicActivity;
import com.bdlm.yytx.module.scenic.TicketListActivity;
import com.bdlm.yytx.module.webview.LoadHtmlActivity;
import com.bdlm.yytx.module.welcome.WelcomeModel;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.app.lib.view.recycleview.adapter.HeaderAndFooterRecycleViewAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends BaseFragment implements IHomeContact.IHomeView, WelcomeModel.WelResultListener {
    @BindView(R.id.title)
    TextView title;
    HomePersenter persenter;
    @BindView(R.id.rv)
    RecyclerView rv;


    private static List<ScenicResponse> tempScenic = new ArrayList<>();
    private BaseRecycleViewAdapter<ScenicResponse> dataAdapter;
    private HeaderAndFooterRecycleViewAdapter adapter;
    private HeadViewHolder headViewHolder;
    private WelcomeModel model;
    private List<String> imgStr=new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void createPresenter() {
        if (tempScenic != null) {
            tempScenic.clear();
        }
        persenter = new HomePersenter(this);
        dataAdapter = new BaseRecycleViewAdapter<ScenicResponse>(tempScenic, R.layout.item_main_scenic) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ScenicResponse scenicResponse = tempScenic.get(position);
                holder.setImage(R.id.iv_scenic, scenicResponse.getThumbnail());
                holder.setText(R.id.tv_scenic_name, scenicResponse.getName());
                holder.setText(R.id.tv_zc, scenicResponse.getPassport_type_name());
            }
        };
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(mContext, 2));
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        dataAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext, ScenicDetailsActivity.class);
                intent.putExtra(Constant.SCENIC_ID, tempScenic.get(position).getSenic_id());
                startActivity(intent);
            }
        });
        persenter.getNotice();
        persenter.getPosition();
        persenter.requestShopUrl();
        model = new WelcomeModel();
        model.setListener(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_main_header, rv, false);
        headViewHolder = new HeadViewHolder(headerView, mContext);
        adapter = new HeaderAndFooterRecycleViewAdapter(dataAdapter);
        adapter.addHeaderView(headerView);
        rv.setAdapter(adapter);
        model.getAdvList("2");
    }

    @Override
    public void resultPosition(PositionBean positionBean) {
        title.setText(positionBean.getProvince());
        if (positionBean != null) {
            PreferenceUtils.getInstance().saveData(Constant.CURLAN, positionBean.getLatitude() + "");
            PreferenceUtils.getInstance().saveData(Constant.CURLON, positionBean.getLongitude() + "");
            //拿到位置信息 请求 附近景区
            String lon = PreferenceUtils.getInstance().getString(Constant.CURLON);
            String lat = PreferenceUtils.getInstance().getString(Constant.CURLAN);
            persenter.requestScenicList(lon, lat, null);
        }
    }

    @Override
    public void resultScenic(final List<ScenicResponse> responses) {

        if (responses != null && responses.size() >= 1) {
            headViewHolder.lin_recomend.setVisibility(View.VISIBLE);
            ImageLoader.display(responses.get(0).getThumbnail(), headViewHolder.ivScenic1);
            ValidatorUtil.setTextVal(headViewHolder.tvScenic1Name, responses.get(0).getName());
            ImageLoader.display(responses.get(1).getThumbnail(), headViewHolder.ivScenic2);
            ValidatorUtil.setTextVal(headViewHolder.tvScenic2, responses.get(1).getName());
            headViewHolder.ivScenic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ScenicDetailsActivity.class);
                    intent.putExtra(Constant.SCENIC_ID, responses.get(0).getSenic_id());
                    startActivity(intent);

                }
            });
            headViewHolder.ivScenic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ScenicDetailsActivity.class);
                    intent.putExtra(Constant.SCENIC_ID, responses.get(1).getSenic_id());
                    startActivity(intent);

                }
            });
        } else {
            headViewHolder.lin_recomend.setVisibility(View.GONE);
        }
        if (tempScenic != null && responses.size() >= 2) {
            tempScenic.addAll(responses.subList(2, responses.size()));
            dataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resultNotice(String noticeStr) {
        headViewHolder.tvGonggao.setSelected(true);
        headViewHolder.tvGonggao.setText(noticeStr);
    }

    @Override
    public void resultTourGoodsUrl(HomeURLBean urlBean) {

        headViewHolder.setUrlBean(urlBean);
    }

    @Override
    public void appInfo(AppVersion appVersion) {

    }

    @Override
    public void adList(List<ImageBean> beanList) {
        for (ImageBean img : beanList) {
            imgStr.add(img.getAd_img());
        }
        headViewHolder.banner.setDelayTime(1500);
        headViewHolder.banner.setImages(imgStr);
        //设置轮播时间
        headViewHolder.banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                com.trsoft.app.lib.utils.ImageLoader.display((String) path,imageView);
            }
        });
        headViewHolder.banner.setDelayTime(1500);
        headViewHolder.banner.start();
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(mContext, msg, null);
    }


    @OnClick({R.id.title, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
                break;
            case R.id.tv_search:
                toActivityNoClear(SearchScenicActivity.class);
                break;
        }
    }


    static class HeadViewHolder {
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
        @BindView(R.id.lin_recomend)
        LinearLayout lin_recomend;
        @BindView(R.id.iv_scenic1)
        ImageView ivScenic1;
        @BindView(R.id.tv_scenic1_name)
        TextView tvScenic1Name;
        @BindView(R.id.iv_scenic2)
        ImageView ivScenic2;
        @BindView(R.id.tv_scenic2)
        TextView tvScenic2;
        private Context context;
        private HomeURLBean urlBean;

        public void setUrlBean(HomeURLBean urlBean) {
            this.urlBean = urlBean;
        }

        HeadViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
        }

        @OnClick({R.id.tv_cash_coupon, R.id.tv_scenic_spot, R.id.tv_tourist_goods, R.id.tv_chi, R.id.tv_live, R.id.tv_play, R.id.tv_travel_agency})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.title:

                    context.startActivity(new Intent(context, SelCityLoginActivity.class));
                    break;

                case R.id.tv_cash_coupon:
                    context.startActivity(new Intent(context, TicketListActivity.class));
                    break;
                case R.id.tv_scenic_spot:
                    context.startActivity(new Intent(context, ScenicListActivity.class));
                    break;
                case R.id.tv_tourist_goods:
                    Intent i = new Intent(context, LoadHtmlActivity.class);

                    i.putExtra(Constant.BUNDLE_STRING, "旅游用品");
                    i.putExtra(Constant.BUNDLE_URL, urlBean.getUrl());
                    context.startActivity(i);
                    break;
                case R.id.tv_chi:
                    Intent intent = new Intent(context, BusinessActivity.class);
                    intent.putExtra("title", BussinessTypeEnum.EAT);
                    context.startActivity(intent);

                    break;
                case R.id.tv_live:
                    Intent intent1 = new Intent(context, BusinessActivity.class);
                    intent1.putExtra("title", BussinessTypeEnum.HOTEL);
                    context.startActivity(intent1);

                    break;
                case R.id.tv_play:
                    Intent intent2 = new Intent(context, BusinessActivity.class);
                    intent2.putExtra("title", BussinessTypeEnum.PLAY);
                    context.startActivity(intent2);

                    break;
                case R.id.tv_travel_agency:
                    Intent intent3 = new Intent(context, BusinessActivity.class);
                    intent3.putExtra("title", BussinessTypeEnum.TRAVEL);
                    context.startActivity(intent3);

                    break;

            }
        }

    }
}
