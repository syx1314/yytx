package com.bdlm.yytx.module.business;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.trsoft.app.lib.view.recycleview.ViewHolder;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by yyj on 2018/2/7.
 */

public class BussinessMutilRvAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final int FIRST = 0x1;
    private final int SECOND = 0x2;
    private final List<BusinessBean> businessBeans;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    DecimalFormat df = new DecimalFormat("#.0");

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BussinessMutilRvAdapter(List<BusinessBean> businessBeans, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.businessBeans = businessBeans;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FIRST) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bussiness_header, null));
        } else {
            return new SecondViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bussiness, null));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        } else {
            SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
            BusinessBean businessBean = businessBeans.get(position - 1);
            holder.setImage(R.id.iv_shop, businessBean.getLogo_img());
            holder.setText(R.id.tv_shop_name,businessBean.getShop_name());
            holder.setText(R.id.tv_bussiness_phone, businessBean.getMobile());
            if (businessBean.getDistance() != null) {
                holder.setText(R.id.tv_diatance, df.format(Double.valueOf(businessBean.getDistance())) + "km");
            }
            holder.setText(R.id.tv_address, businessBean.getAddress());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position <= 0) {
            return FIRST;
        } else {
            return SECOND;
        }
    }

    @Override
    public int getItemCount() {
        return 1 + (businessBeans == null ? 0 : businessBeans.size());
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
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
