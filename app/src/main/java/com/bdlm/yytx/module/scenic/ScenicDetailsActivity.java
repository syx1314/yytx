package com.bdlm.yytx.module.scenic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;

/**
 * 景区详情
 */
public class ScenicDetailsActivity extends BaseActivity implements ScenicContact.IScenicView {
    private ScenicPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_scenic_details;
    }

    @Override
    protected void createPersenter() {
        String scenic_id = getIntent().getStringExtra(Constant.SCENIC_ID);
        presenter = new ScenicPresenter(this);
        if (Validator.isNotEmpty(scenic_id)) {
            presenter.requestScenicDetails(scenic_id);
        }
    }


    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void getScenicList(ScenicListResponse response) {

    }

    @Override
    public void scenicDetails(ScenicDetailResponse response) {

    }
}
