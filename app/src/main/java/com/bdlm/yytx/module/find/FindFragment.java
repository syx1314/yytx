package com.bdlm.yytx.module.find;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.scenic.ScenicDetailsActivity;
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
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position <= 3) {
                    return 1;
                }
                return 2;
            }
        });
        rv.setLayoutManager(manager);
        FindMutilRvAdapter adapter = new FindMutilRvAdapter(map, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new FindMutilRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (position <= 3) {
                    Intent intent = new Intent(mContext, ScenicDetailsActivity.class);
                    intent.putExtra(Constant.SCENIC_ID, ((List<ScenicResponse>) map.get(RECOMEDSCENIC)).get(position).getSenic_id());
                    startActivity(intent);
                }
                if(position>=5){
                    Intent intent = new Intent(mContext, ScenicDetailsActivity.class);
                    intent.putExtra(Constant.SCENIC_ID, ((List<ScenicPlaySortBean>) map.get(PLAYSORTSCENIC)).get(position-5).getSenic_id());
                    startActivity(intent);
                }
            }
        });


    }


}
