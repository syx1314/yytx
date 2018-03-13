package com.bdlm.yytx.module.find;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private OnItemClickListener onItemClickListener;

    List<ScenicResponse> recommendScenic;
    private int recommendIndex = 0;
private Context mContext;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FindMutilRvAdapter(Map<Integer, Object> map, RecyclerView recyclerView) {
        this.map = map;
        this.recyclerView = recyclerView;
        recommendScenic = (List<ScenicResponse>) map.get(FIRST);
        if (recommendScenic != null) {
            recommendIndex = recommendScenic.size();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        if (viewType == FIRST) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_find_header, null));
        } else if (viewType == SECOND) {
            return new SecondViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_find_second, null));

        } else {
            return new ThreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_bottom_sort, null));
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if (position < recommendIndex) {
            if (position == 0) {
                holder.visable(R.id.tv_recommend_tips, View.VISIBLE);
            } else {
                holder.visable(R.id.tv_recommend_tips, View.GONE);
            }
            if (recommendScenic != null) {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                ScenicResponse scenicResponse = recommendScenic.get(position);
                if (scenicResponse != null) {
                    headerViewHolder.setImage(R.id.iv_scenic, scenicResponse.getThumbnail());
                    headerViewHolder.setText(R.id.tv_scenic_name, scenicResponse.getName());
                }
                headerViewHolder.setOnClickListener(R.id.iv_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                });

            }
        } else if (position == recommendIndex) {

        } else {
            List<ScenicPlaySortBean> playSortBeans = (List<ScenicPlaySortBean>) map.get(THREE);
            if (playSortBeans != null) {
                ScenicPlaySortBean playSortBean = playSortBeans.get(position - recommendIndex - 1);
                if (playSortBean != null) {
                    ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
                    threeViewHolder.setImage(R.id.iv_scenic, playSortBean.getThumbnail());
                    threeViewHolder.setText(R.id.tv_scenic_sort_name, playSortBean.getName());
                    threeViewHolder.setText(R.id.tv_scenic_description, playSortBean.getLong_title());
                    threeViewHolder.setText(R.id.tv_people_num, playSortBean.getSenic_num() + "人");
                    threeViewHolder.setOnClickListener(R.id.rl_sort, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(position);
                            }
                        }
                    });
                }

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < recommendIndex) {
            return FIRST;
        } else if (position == recommendIndex) {
            return SECOND;
        } else {
            return THREE;
        }
    }

    @Override
    public int getItemCount() {
        int i = (map.get(FIRST) != null ? recommendIndex : 0) + (map.get(THREE) != null ? ((List<ScenicPlaySortBean>) map.get(THREE)).size() : 0) + 1;
        return i;
    }

    class HeaderViewHolder extends ViewHolder {


        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    class SecondViewHolder extends ViewHolder {


        public SecondViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            itemView.findViewById(R.id.tv_tour_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, TravelInfoListActivity.class));

                }
            });
        }

//        @OnClick(R.id.tv_tour_info)
//        public void toTourInfo(View view) {
//            mContext.startActivity(new Intent(mContext, TravelInfoListActivity.class));
//        }
    }

    class ThreeViewHolder extends ViewHolder {

        public ThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
