package com.bdlm.yytx.module.scenic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.module.webview.LoadHtmlActivity;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 景区详情
 */
public class ScenicDetailsActivity extends BaseActivity implements ScenicContact.IScenicView, TabLayout.OnTabSelectedListener {

    @BindView(R.id.rl_header)
    RelativeLayout relativeLayout;
    @BindView(R.id.title)
    CommonTitle title;
    @BindView(R.id.sv)
    ScrollView scrollView;
    @BindView(R.id.tv_scenic_name)
    TextView tvScenicName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_scenic_address)
    TextView tvScenicAddress;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.btn_kf)
    TextView btnKf;
    @BindView(R.id.btn_vr)
    TextView btnVr;
    @BindView(R.id.btn_ticket_buy)
    Button btnTicketBuy;
    @BindView(R.id.btn_advance)
    Button btnAdvance;
    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.tv_scenic_grade)
    TextView tvScenicGrade;
    @BindView(R.id.tv_is_addvance)
    TextView tvIsAddvance;
    @BindView(R.id.tv_addvance_endDate)
    TextView tvAddvanceEndDate;
    private ScenicPresenter presenter;
    ScenicDetailResponse response;

    @Override
    protected int getLayout() {
        return R.layout.activity_scenic_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void createPersenter() {
        String scenic_id = getIntent().getStringExtra(Constant.SCENIC_ID);
        presenter = new ScenicPresenter(this);

        if (Validator.isNotEmpty(scenic_id)) {
            presenter.requestScenicDetails(scenic_id);
        }
        mImmersionBar.titleBar(relativeLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab3));
        tabLayout.addOnTabSelectedListener(this);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.e("scrollX---" + scrollX + "---scrollY---" + scrollY + "----oldScrollX---" + oldScrollX + "----oldScrollY--" + oldScrollY + "---height--" + tabLayout.getHeight());
                if (scrollY > 10) {
                    if (title.getVisibility() == View.GONE) {
                        title.startAnimation(apperaView());
                        title.setVisibility(View.VISIBLE);
                        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
                    }
                } else {
                    if (title.getVisibility() == View.VISIBLE) {
                        title.startAnimation(disApperaView());
                        title.setVisibility(View.GONE);
                        mImmersionBar.titleBar(relativeLayout);
                        mImmersionBar.statusBarColorInt(0x00000000).init();
                    }
                }
            }
        });


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
        this.response = response;
        if (response != null) {
            ImageLoader.display(response.getImage(), relativeLayout);
            ValidatorUtil.setTextVal(tvScenicGrade, response.getLevel_name());
            ValidatorUtil.setTextVal(tvScenicName, response.getName() + response.getLong_title());
            ValidatorUtil.setTextVal(tvPrice,response.getPrice());
            ValidatorUtil.setTextVal(tvScenicAddress, response.getAddress());
            if (response.getAdvance() == 1) {
                tvIsAddvance.setTextSize(14);
                ValidatorUtil.setTextVal(tvIsAddvance,getString(R.string.scenic_addvance));
                tvPrice.setVisibility(View.GONE);
                tvAddvanceEndDate.setText(String.format(getString(R.string.scenic_addvance_end_date), response.getAdvance_enddate()));
            }else {
                ValidatorUtil.setTextVal(tvIsAddvance,getString(R.string.scenic_hand_passport));
            }
            addPlayLayout(tabLayout.getTabAt(0));
        }
    }

    @Override
    public void passportType(List<PassportTypeBean> passportTypeBeans) {
    }

    //添加详情页面的布局
    private void addPlayLayout(TabLayout.Tab tab) {
        flContent.removeAllViews();
        if (tab.getText().equals("游玩须知")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.layout_scenic_note, flContent);
            TextView tvOpenTime = view.findViewById(R.id.tv_open_time);
            TextView tvTraffic = view.findViewById(R.id.tv_traffic);
            TextView tvYhzc = view.findViewById(R.id.tv_yyzc);
            TextView tvNote = view.findViewById(R.id.tv_note);
            ValidatorUtil.setTextVal(tvOpenTime, response.getOpen());
            ValidatorUtil.setTextVal(tvTraffic, response.getTraffic());
            ValidatorUtil.setTextVal(tvYhzc, response.getTicket());
            ValidatorUtil.setTextVal(tvNote, response.getNotice());
        } else if (tab.getText().equals("图文详情")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.layout_webview, flContent);
            WebView webView = view.findViewById(R.id.webView);
            webView.loadUrl(Constant.BASEURL+"/Senic/getDescription/senic_id/" + response.getSenic_id());
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        addPlayLayout(tab);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @OnClick({R.id.btn_kf, R.id.btn_vr, R.id.btn_ticket_buy, R.id.btn_advance, R.id.ic_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent(activity, LoadHtmlActivity.class);
        switch (view.getId()) {
            case R.id.btn_kf:
                if (Validator.isNotEmpty(response.getTelephone()))
                    toCallPhone(response.getTelephone());
                break;
            case R.id.btn_vr:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.xnlv));
                intent.putExtra(Constant.BUNDLE_URL, response.getVr_url());
                startActivity(intent);
                break;
            case R.id.btn_ticket_buy:

                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.buy_ticket));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Ticket/showlist/senic_id/1847/user_name/18336407333");
                startActivity(intent);
                break;
            case R.id.btn_advance:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.advance));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Advance/index/user_name/18336407333/sid/750");
                startActivity(intent);
                break;
            case R.id.ic_back:
               onBackPressed();
                break;
        }

    }

    //出现的动画
    private AlphaAnimation apperaView() {
        AlphaAnimation appearAnimation = new AlphaAnimation(0, 1);
        appearAnimation.setDuration(1000 * 2);
        return appearAnimation;

    }
    //消失的动画
    private AlphaAnimation disApperaView() {
        AlphaAnimation disappearAnimation = new AlphaAnimation(1, 0);
        disappearAnimation.setDuration(1000 * 2);
        return disappearAnimation;
    }
}
