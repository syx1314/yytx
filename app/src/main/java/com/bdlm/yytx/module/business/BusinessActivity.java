package com.bdlm.yytx.module.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.common.view.CommonTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessActivity extends BaseActivity {

    @BindView(R.id.ct_title)
    CommonTitle ctTitle;
    @BindView(R.id.rv_business)
    RecyclerView rvBusiness;

    @Override
    protected int getLayout() {
        return R.layout.activity_business;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        ctTitle.setClickFun(new CommonTitle.IClickFun() {
            @Override
            public void leftOclick() {
                onBackPressed();
            }

            @Override
            public void rightOclick() {
                toActivityNoClear(BusinessJoinActivity.class);
            }
        });
    }


}
