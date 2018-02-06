package com.bdlm.yytx.module.scenic;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.Page;
import com.bdlm.yytx.entity.TicketBean;
import com.bdlm.yytx.entity.TicketListResponse;
import com.bdlm.yytx.module.webview.LoadHtmlLoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TicketListActivity extends BaseLoginActivity implements ScenicContact.ITicketView, OnRefreshLoadmoreListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ScenicPresenter presenter;
    private BaseRecycleViewAdapter<TicketBean> adapter;
    private List<TicketBean> ticketBeanList;
    private Page pageInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_ticket_list;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
        presenter = new ScenicPresenter(this);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
        presenter.requestTicketList("1");
        ticketBeanList = new ArrayList<>();
        adapter = new BaseRecycleViewAdapter<TicketBean>(ticketBeanList, R.layout.item_scenic_ticket) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TicketBean ticketBean = ticketBeanList.get(position);

                holder.setImage(R.id.iv_scenic, ticketBean.getThumbnail());
                holder.setText(R.id.tv_scenic_name, ticketBean.getName());
                holder.setText(R.id.tv_price, ticketBean.getPrice());
                holder.setText(R.id.tv_scenic_grade, ticketBean.getLevel_name());


                if (Validator.isNotEmpty(ticketBean.getAddress())) {
                    holder.visable(R.id.tv_address, View.VISIBLE);
                    holder.setText(R.id.tv_address, ticketBean.getAddress());
                } else {
                    holder.visable(R.id.tv_address, View.GONE);
                }

            }
        };
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                buyTicket(ticketBeanList.get(position).getSenic_id());
            }
        });
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void resultTicketList(TicketListResponse ticketListResponse) {
        pageInfo = ticketListResponse.getPageInfo();
        ticketBeanList.addAll(ticketListResponse.getSenicList());
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadmore();
        }
        adapter.notifyDataSetChanged();

    }

    public void buyTicket(String scenic_id) {
        isLogin();
        Intent intent = new Intent(activity, LoadHtmlLoginActivity.class);
        intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.buy_ticket));
        intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Ticket/showlist/senic_id/" + scenic_id + "/token/" + token);
        startActivity(intent);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageInfo.getCurPage() < pageInfo.getCountPage()) {
            presenter.requestTicketList(pageInfo.getNextPage() + "");
        } else {
            refreshlayout.finishLoadmoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
