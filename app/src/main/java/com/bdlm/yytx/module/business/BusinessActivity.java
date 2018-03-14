package com.bdlm.yytx.module.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.BussinessTypeEnum;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.Page;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//加盟商户列表
public class BusinessActivity extends BaseActivity implements BusinessContact.IBussinessListView, OnLoadmoreListener {

    @BindView(R.id.ct_title)
    CommonTitle ctTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView rvBusiness;
    BusinessPersenter persenter;
    BussinessMutilRvAdapter adapter;
    private List<BusinessBean> businessBeans = new ArrayList<>();
    private Page pageInfo;
    private String lat;
    private String lon;
    private String manage_type;
    private BussinessTypeEnum bussinessTypeEnum;

    @Override
    protected int getLayout() {
        return R.layout.activity_business;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        persenter = new BusinessPersenter(this);
        bussinessTypeEnum = (BussinessTypeEnum) getIntent().getSerializableExtra("title");
        manage_type = bussinessTypeEnum.getCode() + "";
        ctTitle.setTvTitle(bussinessTypeEnum.getName());
        ctTitle.setClickFun(new CommonTitle.IClickFun() {
            @Override
            public void leftOclick() {
                onBackPressed();
            }

            @Override
            public void rightOclick() {
                Intent intent = new Intent(activity, BusinessJoinActivity.class);
                intent.putExtra("title", bussinessTypeEnum);
                startActivity(intent);

            }
        });
        lat = PreferenceUtils.getInstance().getString(Constant.CURLAN);
        lon = PreferenceUtils.getInstance().getString(Constant.CURLON);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableRefresh(false);
        adapter = new BussinessMutilRvAdapter(businessBeans, rvBusiness);
        rvBusiness.setLayoutManager(new LinearLayoutManager(activity));
        rvBusiness.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (businessBeans != null) {
            businessBeans.clear();
            adapter.notifyDataSetChanged();
        }
        persenter.requestBussinessList(manage_type, lon, lat, "1");

    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void resultBussinessList(BusinessListResponse response) {
        pageInfo = response.getPageInfo();
        if (response != null && response.getPartnerList() != null) {
            businessBeans.addAll(response.getPartnerList());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageInfo != null && pageInfo.getCurPage() < pageInfo.getCountPage()) {
            persenter.requestBussinessList(manage_type, lon, lat, pageInfo.getNextPage() + "");

        } else {
            refreshlayout.finishLoadmoreWithNoMoreData();
        }
    }
}
