package com.bdlm.yytx.module.login;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.LoginResponse;
import com.trsoft.app.lib.http.ApiResultBean;

import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginLoginActivity extends BaseLoginActivity implements LoginContact.ILoginView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_approve)
    CheckBox cbApprove;

    private LoginPresenter loginPresenter;
    private CountDownTimer timer;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void createPersenter() {
        loginPresenter = new LoginPresenter(this);
        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4) {
                    btnLogin.setEnabled(true);
                }else {
                    btnLogin.setEnabled(false);
                }
            }
        });
    }


    @OnClick(R.id.tv_send_code)
    public void onSendCode() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                tvSendCode.setText(l / 1000 + "");
            }
            @Override
            public void onFinish() {
                tvSendCode.setEnabled(true);
            }
        };
        String phone = etPhone.getText().toString();
        if (Validator.isNotEmpty(phone)) {
            if (Validator.isMobile(phone)) {
                loginPresenter.sendCode(phone);
                timer.start();
            } else {
                DialogUtil.showAlert(activity, "手机号有误", null);
            }
        } else {
            DialogUtil.showAlert(activity, "请输入验证码", null);
        }


    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        if (cbApprove.isChecked()) {
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            if (Validator.isNotEmpty(phone) && Validator.isNotEmpty(code)) {
                loginPresenter.login(phone, code);
            } else {
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
        if (apiResult.getCode() == 2000) {
            tvSendCode.setEnabled(false);
        }
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
