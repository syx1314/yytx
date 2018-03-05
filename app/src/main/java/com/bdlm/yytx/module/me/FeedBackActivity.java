package com.bdlm.yytx.module.me;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.constant.Constant;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//意见反馈
public class FeedBackActivity extends BaseLoginActivity implements MeContact.IFeedBackView {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_feed_back_content)
    EditText etFeedBackContent;
    @BindView(R.id.btn_hand)
    Button btnHand;
    MePresenter presenter;
    private String mobile;

    @Override
    protected int getLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        presenter = new MePresenter(this);
        mobile = PreferenceUtils.getInstance().getString(Constant.MOBILE);
        ValidatorUtil.setTextVal(tvPhone, "手机号:" + mobile);
    }


    @OnClick(R.id.btn_hand)
    public void onViewClicked() {

        if (Validator.isNotEmpty(etFeedBackContent.getText().toString())) {
            //提交
            presenter.submitFeedBack(mobile, etFeedBackContent.getText().toString());
        } else {
            DialogUtil.showAlert(activity, getString(R.string.me_hand_opinion_tips), null);
        }
    }

    @Override
    public void error(String msg) {
        MyLog.e("ajailjaklajl");
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void resultFeedBack(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }
}
