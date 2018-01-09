package com.bdlm.yytx.module.scenic;

import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 景区详情
 */
public class ScenicDetailsActivity extends BaseActivity implements ScenicContact.IScenicView {

    @BindView(R.id.rl_header)
    FrameLayout frameLayout;
    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.tv_scenic_name)
    TextView tvScenicName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_scenic_address)
    TextView tvScenicAddress;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
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
        mImmersionBar.titleBar(frameLayout);
      tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab1));
      tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab2));
      tabLayout.addTab(tabLayout.newTab().setText(R.string.scenic_tab3));
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
           if(response!=null){
//               ImageLoader.display(response.getImage(),ivScenic);
               ImageLoader.display(response.getImage(),frameLayout);
               ValidatorUtil.setTextVal(tvScenicName,response.getName()+response.getLong_title());
               ValidatorUtil.setTextVal(tvPrice,"¥"+response.getPrice());
               ValidatorUtil.setTextVal(tvScenicAddress,response.getAddress());
           }
    }

    @Override
    public void passportType(List<PassportTypeBean> passportTypeBeans) {

    }

}
