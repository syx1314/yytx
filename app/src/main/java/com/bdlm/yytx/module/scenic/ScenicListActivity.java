package com.bdlm.yytx.module.scenic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.Page;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 景区列表
 */
public class ScenicListActivity extends BaseActivity implements ScenicContact.IScenicView, OnLoadmoreListener, BaseRecycleViewAdapter.OnItemClickListener, TabLayout.OnTabSelectedListener {
    ScenicPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private Page pageInfo;
    private List<ScenicResponse> senicList;
    private BaseRecycleViewAdapter<ScenicResponse> adapter;
    private List<PassportTypeBean> typeBeans;
    private String lon;
    private String lat;
    private int passPortType;

    @Override
    protected int getLayout() {
        return R.layout.activity_scenic_list;
    }

    @Override
    protected void createPersenter() {
        presenter = new ScenicPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableRefresh(false);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
        mImmersionBar.statusBarColor(R.color.red).init();
        //获取护照类型
        presenter.requestPassportType();
        tablayout.addOnTabSelectedListener(this);
        lon = PreferenceUtils.getInstance().getString(Constant.CURLON);
        lat = PreferenceUtils.getInstance().getString(Constant.CURLAN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (senicList == null) {
            senicList = new ArrayList<>();
            adapter = new BaseRecycleViewAdapter<ScenicResponse>(senicList, R.layout.item_scenic) {
                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);

                    ScenicResponse response = senicList.get(position);
                    if (response.getAdvance() == 1) {
                        holder.setLableText(R.id.labelView, "预约");
                    } else {
                        holder.setLableText(R.id.labelView, null);
                    }
                    holder.setImage(R.id.iv_scenic, response.getThumbnail());
                    holder.setText(R.id.tv_scenic_name, response.getName());
                    holder.setText(R.id.tv_distance, response.getDistance() + "km");
                    holder.setText(R.id.tv_scenic_type, response.getCate_name());
                    holder.setText(R.id.tv_scenic_grade, response.getLevel_name());
                    holder.setText(R.id.tv_passport_type, response.getPassport_type_name());
                    holder.setText(R.id.tv_short_description, response.getShort_description());
                }
            };
            rv.setAdapter(adapter);

        } else {
//            senicList.clear();
        }
        adapter.setOnItemClickListener(this);


    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void getScenicList(final ScenicListResponse response) {
        pageInfo = response.getPageInfo();
        senicList.addAll(response.getSenicList());
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void scenicDetails(ScenicDetailResponse response) {

    }

    @Override
    public void passportType(List<PassportTypeBean> passportTypeBeans) {
        //护照类型
        typeBeans = passportTypeBeans;
        if (typeBeans != null && typeBeans.size() > 0) {
            for (PassportTypeBean type : typeBeans) {
                tablayout.addTab(tablayout.newTab().setText(type.getName()));
            }
        }
        //请求景区
        presenter.requestScenicList(lon, lat, passPortType, 0, 1);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageInfo.getCurPage() < pageInfo.getCountPage()) {
            presenter.requestScenicList(lon, lat, passPortType, 0, pageInfo.getNextPage());
        } else {
            refreshlayout.finishLoadmoreWithNoMoreData();
        }
    }

    @Override
    public void onClick(int position) {
        if (activity != null) {
            if (senicList != null && senicList.size() > 0) {
                Intent intent = new Intent(activity, ScenicDetailsActivity.class);
                intent.putExtra(Constant.SCENIC_ID, senicList.get(position).getSenic_id());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(refreshLayout.isLoading()){
            refreshLayout.finishLoadmore();
        }
        presenter.cancelRequest();
        refreshLayout.resetNoMoreData();

        passPortType = typeBeans.get(tab.getPosition()).getId();
        if(senicList!=null){
            senicList.clear();
            adapter.notifyDataSetChanged();
            pageInfo=null;
            presenter.requestScenicList(lon, lat, passPortType, 0, 1);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
