package com.bdlm.yytx.module.me;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.entity.UserInfoBean;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
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


        if(userInfoBean!=null){
            ValidatorUtil.setTextVal(tvName,userInfoBean.getNick_name());
            ValidatorUtil.setTextVal(tvPassportNum,mContext.getString(R.string.me_passport_num)+userInfoBean.getPassport_num());

            ImageLoader.displayCircleImage(mContext,userInfoBean.getAvatar(),ivHead);
        }
    }


}
