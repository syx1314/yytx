package com.bdlm.yytx.module.business;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.entity.BusinessBean;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商家加盟
 */
public class BusinessJoinActivity extends BaseLoginActivity implements BusinessContact.IBusinessView {

    @BindView(R.id.et_business_name)
    EditText etBusinessName;
    @BindView(R.id.et_business_discount)
    EditText etBusinessDiscount;
    @BindView(R.id.et_business_phone)
    EditText etBusinessPhone;
    @BindView(R.id.et_business_address)
    EditText etBusinessAddress;
    @BindView(R.id.et_feature)
    EditText etFeature;
    @BindView(R.id.iv_business_banner)
    ImageView ivBusinessBanner;
    @BindView(R.id.btn_approve)
    Button btnApprove;
    BusinessPersenter persenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_business_join;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        persenter = new BusinessPersenter(this);
    }

    //选择店铺招牌
    @OnClick(R.id.iv_business_banner)
    public void selImg(View view) {

    }

    @OnClick(R.id.et_business_address)
    public void selAddress(View view) {
        
    }

    //提交审核
    @OnClick(R.id.btn_approve)
    public void setBtnApprove(View view) {
        String name = etBusinessName.getText().toString();
        String discount = etBusinessDiscount.getText().toString();
        String phone = etBusinessPhone.getText().toString();
        String address = etBusinessAddress.getText().toString();
        String feature = etFeature.getText().toString();

        if (TextUtils.isEmpty(name)) {
            DialogUtil.showAlert(activity, getString(R.string.business_address_hint), null);
            return;
        }
        if (TextUtils.isEmpty(discount)) {
            DialogUtil.showAlert(activity, getString(R.string.business_discount_hint), null);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            DialogUtil.showAlert(activity, getString(R.string.business_phone_hint), null);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            DialogUtil.showAlert(activity, getString(R.string.business_address_hint), null);
            return;
        }
        if (TextUtils.isEmpty(feature)) {
            DialogUtil.showAlert(activity, getString(R.string.business_feature_hint), null);
            return;
        }

        BusinessBean businessBean = new BusinessBean();
        businessBean.setAddress(address);
        businessBean.setDescribe(feature);
        businessBean.setDiscount_info(discount);
        businessBean.setMobile(phone);
        businessBean.setShop_name(name);
        persenter.submit(businessBean);

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void resultApprove(String msg) {

    }
}
