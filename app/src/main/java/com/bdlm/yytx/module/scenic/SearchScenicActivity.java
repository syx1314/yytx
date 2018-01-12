package com.bdlm.yytx.module.scenic;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchScenicActivity extends BaseActivity {

    @BindView(R.id.et_search_key)
    EditText etSearchKey;
    @BindView(R.id.et_cancel)
    TextView etCancel;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_scenic;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
    }


    @OnClick(R.id.et_cancel)
    public void onViewClicked() {
        onBackPressed();
    }
}
