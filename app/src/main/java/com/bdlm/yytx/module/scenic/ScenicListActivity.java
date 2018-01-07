package com.bdlm.yytx.module.scenic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.Page;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicResponse;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 景区列表
 */
public class ScenicListActivity extends BaseActivity implements ScenicContact.IScenicView, OnLoadmoreListener, BaseRecycleViewAdapter.OnItemClickListener {
    ScenicPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Page pageInfo;
    private List<ScenicResponse> senicList;
    private BaseRecycleViewAdapter<ScenicResponse> adapter;

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

        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
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
            senicList.clear();
        }
        adapter.setOnItemClickListener(this);
        presenter.requestScenicList(116.1914062500, 39.6056881783, 0, 0, 1);

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
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageInfo.getCurPage() < pageInfo.getCountPage()) {
            presenter.requestScenicList(116.1914062500, 39.6056881783, 0, 0, pageInfo.getNextPage());
        } else {
            refreshlayout.finishLoadmoreWithNoMoreData();
        }
    }

    @Override
    public void onClick(int position) {
        if (activity != null) {
            Intent intent = new Intent(activity, ScenicDetailsActivity.class);
            intent.putExtra(Constant.SCENIC_ID, senicList.get(position).getSenic_id());
            startActivity(intent);
        }
    }
}
