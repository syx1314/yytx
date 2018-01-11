package com.bdlm.yytx.module.me;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.UserInfoBean;
import com.bdlm.yytx.module.login.LoginActivity;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MeFragment extends BaseFragment<MePresenter> implements MeContact.IMeView {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_passport_num)
    TextView tvPassportNum;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_advance_record)
    TextView tvAdvanceRecord;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_qrcode)
    TextView tvQrcode;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    Unbinder unbinder;
    private MePresenter presenter;

    @Override
    protected void createPresenter() {
        presenter = new MePresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.userInfo();
    }

    @Override
    public void error(String msg) {
        Logger.e(msg);
        DialogUtil.showAlert(mContext, msg, null);
    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {


        if (userInfoBean != null) {
            ValidatorUtil.setTextVal(tvName, userInfoBean.getNick_name());
            ValidatorUtil.setTextVal(tvPassportNum, mContext.getString(R.string.me_passport_num) + userInfoBean.getPassport_num());

            ImageLoader.displayCircleImage(mContext, userInfoBean.getAvatar(), ivHead);
        }
    }

    @Override
    public void responseLogout(String msg) {
        DialogUtil.showAlert(mContext, msg, new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {
                PreferenceUtils.getInstance().clear();
                toActivityNoClear(LoginActivity.class);
            }
        });
    }

    @OnClick({R.id.tv_exchange, R.id.tv_bind, R.id.tv_advance_record, R.id.tv_recommend, R.id.tv_qrcode, R.id.tv_order, R.id.tv_about, R.id.tv_opinion, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exchange:
                break;
            case R.id.tv_bind:
                break;
            case R.id.tv_advance_record:
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_qrcode:
                break;
            case R.id.tv_order:
                break;
            case R.id.tv_about:
                break;
            case R.id.tv_opinion:
                break;
            case R.id.tv_exit:
              presenter.requestLogout();
                break;
        }
    }
}
