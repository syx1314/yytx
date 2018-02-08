package com.bdlm.yytx.module.find;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 发现
 */
public class FindFragment extends BaseFragment implements IFindContact.IFindView {

    FindPersenter persenter;
    @BindView(R.id.rv_find)
    RecyclerView rv;
    private final int RECOMEDSCENIC = 0x1;
    private final int PLAYSORTSCENIC = 0x3;
    private Map<Integer, Object> map;

    @Override
    protected void createPresenter() {
        persenter = new FindPersenter(this);
        persenter.requestRecommendScenic();
        map = new ArrayMap<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }


    @Override
    public void error(String msg) {

    }

    @Override
    public void resultScenic(List<ScenicResponse> responses) {
        //添加推荐景区的数据
        map.put(RECOMEDSCENIC, responses);
        persenter.requestSortScenic();
    }

    @Override
    public void resultSortScenic(List<ScenicPlaySortBean> responses) {

        //添加游玩景区的数据
        map.put(PLAYSORTSCENIC, responses);
        rv.setLayoutManager(new GridLayoutManager(mContext,2));
        rv.setAdapter(new FindMutilRvAdapter(map,rv));

    }


}
