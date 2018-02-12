package com.bdlm.yytx.module.me;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.UserInfoBean;
import com.bdlm.yytx.module.login.LoginLoginActivity;
import com.bdlm.yytx.module.webview.LoadHtmlLoginActivity;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class MeFragment extends BaseFragment<MePresenter> implements MeContact.IMeView {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_passport_num)
    TextView tvPassportNum;
    @BindView(R.id.tv_passport_type)
    TextView tvPassportType;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
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
        DialogUtil.showAlert(mContext, msg, new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {
                toActivity(LoginLoginActivity.class);
            }
        });

    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {


        if (userInfoBean != null) {
            ValidatorUtil.setTextVal(tvName, userInfoBean.getNick_name());
            ValidatorUtil.setTextVal(tvPassportNum, mContext.getString(R.string.me_passport_num) + userInfoBean.getPassport_num());
            ValidatorUtil.setTextVal(tvPassportType,mContext.getString(R.string.me_passport_type)+userInfoBean.getType_name());
            tvBalance.setText("¥" + userInfoBean.getBalance());
            ImageLoader.displayCircleImage(mContext, userInfoBean.getAvatar(), ivHead);
        }
    }

    @Override
    public void responseLogout(String msg) {
        DialogUtil.showAlert(mContext, msg, new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {
                PreferenceUtils.getInstance().clear();
                toActivityNoClear(LoginLoginActivity.class);
            }
        });
    }

    @OnClick({R.id.lin_exchange, R.id.tv_bind, R.id.tv_advance_record, R.id.tv_recommend, R.id.tv_qrcode, R.id.tv_order, R.id.tv_about, R.id.tv_opinion, R.id.tv_exit})
    public void onViewClicked(View view) {
        Intent intent = new Intent(mContext, LoadHtmlLoginActivity.class);
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        switch (view.getId()) {
            case R.id.lin_exchange:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_exchange));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Index/rechargeBalance?&token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_bind:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_bind_card));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Index/bindPassport?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_advance_record:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_addvance_recored));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Advance/showlist?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_recommend:


//                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_recommend_friends));
//                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Distribution/recommend?token=" + token);
//                startActivity(intent);
                showShare( Constant.BASEURL2 + "/Distribution/recommend?token=" + token);
                break;
            case R.id.tv_qrcode:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_qrcode));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Distribution/qrcode?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_order:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_order));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Ticket/orderList?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_about:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_about_me));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL + "/User/get_system_config/type/ABOUTUS");
                startActivity(intent);
                break;
            case R.id.tv_opinion:
                break;
            case R.id.tv_exit:
                presenter.requestLogout();
                break;
        }
    }

    private void showShare(String str) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("亿游天下");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(str);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("欢饮您加入亿游天下旅游网");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(str);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("欢饮您加入亿游天下旅游网");
        // 启动分享GUI
        oks.show(getActivity());
    }

}
