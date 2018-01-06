package com.bdlm.yytx.module.scenic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.trsoft.app.lib.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 景区列表
 */
public class ScenicListActivity extends BaseActivity implements ScenicContact.IScenicView, OnLoadmoreListener {
    ScenicPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.requestScenicList(116.1914062500, 39.6056881783, 0, 0, 1);
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void getScenicList(ScenicListResponse response) {
        DialogUtil.showAlert(activity, response.getPageInfo().getCountPage() + "?????????", null);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }
}
