package com.bdlm.yytx.module.scenic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.utils.DialogUtil;

public class ScenicListActivity extends BaseActivity implements ScenicContact.IScenicView {

    private ScenicPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_scenic_list;
    }

    @Override
    protected void createPersenter() {
        presenter = new ScenicPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.requestScenicList(99.6679687500,38.30718056,0,0,1);
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void getScenicList(ScenicListResponse response) {

        if(response!=null){
            DialogUtil.showAlert(activity,response.getPageInfo().getCountPage()+"******",null);
        }
    }
}
