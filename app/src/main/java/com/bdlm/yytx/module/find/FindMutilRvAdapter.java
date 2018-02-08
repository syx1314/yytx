package com.bdlm.yytx.module.find;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static com.trsoft.app.lib.BaseApplication.mContext;

/**
 * Created by yyj on 2018/2/7.
 */

public class FindMutilRvAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final int FIRST = 0x1;
    private final int SECOND = 0x2;
    private final int THREE = 0x3;
    private Map<Integer, Object> map;
    private RecyclerView recyclerView;
    public FindMutilRvAdapter(Map<Integer, Object> map,RecyclerView recyclerView) {
        this.map = map;
        this.recyclerView=recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FIRST) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_find_header, null));
        }
// else if (viewType == SECOND) {
//            return null;}
 else {
            return new ThreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_bottom_sort, null));
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position <= 4) {
            List<ScenicResponse> recommendScenic = (List<ScenicResponse>) map.get(FIRST);
            if (recommendScenic != null) {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                ScenicResponse scenicResponse = recommendScenic.get(position);
                if(scenicResponse!=null) {
                    headerViewHolder.setImage(R.id.iv_scenic, scenicResponse.getThumbnail());
                    headerViewHolder.setText(R.id.tv_scenic_name,scenicResponse.getName());
                }

            }
        }
//        else if (position == 5) {
//
//    }
        else {
            List<ScenicPlaySortBean> playSortBeans = (List<ScenicPlaySortBean>) map.get(THREE);
            if(playSortBeans!=null){
                ScenicPlaySortBean playSortBean = playSortBeans.get(position);
                if(playSortBean!=null){
                    ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
                    threeViewHolder.setImage(R.id.iv_scenic,playSortBean.getThumbnail());
                    threeViewHolder.setText(R.id.tv_scenic_name,playSortBean.getName());
                    threeViewHolder.setText(R.id.tv_scenic_description,playSortBean.getLong_title());
                    threeViewHolder.setText(R.id.tv_people_num,playSortBean.getSenic_num());
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position <= 4) {
//                        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
            return FIRST;
        }
//        else if (position == 5) {
//            return SECOND;
//        }
        else {
//            LinearLayoutManager manager = new LinearLayoutManager(mContext);
//            manager.setOrientation(LinearLayoutManager.VERTICAL);
//            recyclerView.setLayoutManager(manager);
            return THREE;
        }
    }

    @Override
    public int getItemCount() {
        return (map.get(FIRST) != null ? ((List<ScenicResponse>)map.get(FIRST)).size() : 0) + (map.get(THREE) != null ? ((List<ScenicPlaySortBean>)map.get(THREE)).size() : 0);
    }

    class HeaderViewHolder extends ViewHolder {


        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    class ThreeViewHolder extends ViewHolder {

        public ThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
