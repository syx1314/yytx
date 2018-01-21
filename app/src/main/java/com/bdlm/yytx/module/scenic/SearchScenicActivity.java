package com.bdlm.yytx.module.scenic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicResponse;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.view.recycleview.RecycleViewDivider;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchScenicActivity extends BaseActivity implements ScenicContact.IScenicView, BaseRecycleViewAdapter.OnItemClickListener {

    @BindView(R.id.et_search_key)
    EditText etSearchKey;
    @BindView(R.id.et_cancel)
    TextView etCancel;
    @BindView(R.id.rv)
    RecyclerView rv;
    ScenicPresenter presenter;
    private List<ScenicResponse> senicList;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_scenic;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.statusBarColor(R.color.color_status_bar).init();
        presenter = new ScenicPresenter(this);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL));
        etSearchKey.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchScenicActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    presenter.requestScenicSearch(etSearchKey.getText().toString());
                }
                return false;
            }
        });
    }


    @OnClick(R.id.et_cancel)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
    }

    @Override
    public void getScenicList(ScenicListResponse response) {
        senicList = response.getSenicList();
        BaseRecycleViewAdapter<ScenicResponse> adapter = new BaseRecycleViewAdapter<ScenicResponse>(senicList, R.layout.item_scenic) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ScenicResponse response = senicList.get(position);
                if (response.getAdvance() == 1) {
                    holder.setLableText(R.id.labelView, "预约");
                } else {
                    holder.setLableText(R.id.labelView, null);
                }
                holder.setImage(R.id.iv_scenic, response.getThumbnail());
                holder.setText(R.id.tv_scenic_name, response.getName());
                holder.setText(R.id.tv_distance, response.getDistance() + "km");
                holder.setText(R.id.tv_scenic_type, response.getCate_name());
                holder.setText(R.id.tv_scenic_grade, response.getLevel_name());
                holder.setText(R.id.tv_passport_type, response.getPassport_type_name());
                holder.setText(R.id.tv_short_description, response.getShort_description());
            }
        };
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void getScenicList(List<ScenicResponse> response) {

    }

    @Override
    public void scenicDetails(ScenicDetailResponse response) {

    }

    @Override
    public void passportType(List<PassportTypeBean> passportTypeBeans) {

    }

    @Override
    public void onClick(int position) {
        if (activity != null) {
            if (senicList != null && senicList.size() > 0) {
                Intent intent = new Intent(activity, ScenicDetailsActivity.class);
                intent.putExtra(Constant.SCENIC_ID, senicList.get(position).getSenic_id());
                startActivity(intent);
            }
        }
    }
}
