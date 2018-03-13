package com.bdlm.yytx.module.find;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.Page;
import com.bdlm.yytx.entity.TourInfoListBean;
import com.bdlm.yytx.entity.TourInfoListResponse;
import com.bdlm.yytx.module.webview.LoadHtmlActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 旅游资讯列表
 */
public class TravelInfoListActivity extends BaseActivity implements IFindContact.ITourInfoListView, OnLoadmoreListener {

    FindPersenter persenter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Page pageInfo;
    private List<TourInfoListBean> tourInfoList = new ArrayList<>();
    private BaseRecycleViewAdapter<TourInfoListBean> adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_travel_info_list;
    }

    @Override
    protected void createPersenter() {
        persenter = new FindPersenter(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableRefresh(false);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
        mImmersionBar.statusBarColor(R.color.red).init();
        adapter = new BaseRecycleViewAdapter<TourInfoListBean>(tourInfoList, R.layout.item_tour_info_list) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_tour_title, mdatas.get(position).getTitle());
                holder.setImage(R.id.iv_tour_info, mdatas.get(position).getTour_img());
                holder.setText(R.id.tv_short_description, mdatas.get(position).getSimple_introduction());
            }
        };
        rv.setAdapter(adapter);
        persenter.requestTourInfoList("1");
        adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(activity, LoadHtmlActivity.class);
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.find_info_details));
                intent.putExtra(Constant.BUNDLE_URL, tourInfoList.get(position).getTour_url());
                startActivity(intent);
            }
        });
    }

    @Override
    public void error(String msg) {
        DialogUtil.showToastCust(msg);
    }

    @Override
    public void resultTourInfoList(TourInfoListResponse tourInfoList) {
        if (tourInfoList != null) {
            pageInfo = tourInfoList.getPageInfo();
            this.tourInfoList.addAll(tourInfoList.getTourInfoList());
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageInfo!=null&&pageInfo.getCurPage() < pageInfo.getCountPage()) {
            persenter.requestTourInfoList(pageInfo.getNextPage()+"");
        } else {
            refreshlayout.finishLoadmoreWithNoMoreData();
        }
    }
}
