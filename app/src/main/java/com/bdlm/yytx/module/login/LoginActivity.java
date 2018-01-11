package com.bdlm.yytx.module.login;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;

import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements LoginContact.ILoginView{

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.cb_approve)
    CheckBox cbApprove;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void createPersenter() {
        loginPresenter = new LoginPresenter(this);
        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
    }


    @OnClick(R.id.tv_send_code)
    public void onSendCode() {
        String phone = etPhone.getText().toString();
        if (Validator.isNotEmpty(phone)) {
            if (Validator.isMobile(phone)) {
                loginPresenter.sendCode(phone);
            } else {
                DialogUtil.showAlert(activity, "手机号有误", null);
            }
        } else {
            DialogUtil.showAlert(activity, "请输入手机号", null);
        }
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        if (cbApprove.isChecked()) {
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            if (Validator.isNotEmpty(phone) && Validator.isNotEmpty(code)) {
                loginPresenter.login(phone, code);
            }else {
                DialogUtil.showAlert(activity, "请输入手机号", null);
            }
        } else {
            DialogUtil.showAlert(activity, getString(R.string.login_approve), null);
        }
    }

    @Override
    public void error(String msg) {
        if (Validator.isNotEmpty(msg)) {
            DialogUtil.showAlert(activity, msg, null);
        }
    }

    @Override
    public void sendCodeResult(ApiResultBean<List<String>> apiResult) {
        DialogUtil.showAlert(activity, apiResult.getMsg(), null);
    }

    @Override
    public void loginResult(LoginResponse loginResponse) {
        if (loginResponse != null && Validator.isNotEmpty(loginResponse.getToken())) {
            DialogUtil.showAlert(activity, getString(R.string.login_success), new CommonCallback<Boolean>() {
                @Override
                public void onCallBack(Boolean data) {
                    finish();
                }
            });
            PreferenceUtils.getInstance().saveData(Constant.TOKEN, loginResponse.getToken());

        }

    }
}
