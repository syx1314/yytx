package com.bdlm.yytx.module.find;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.entity.ScenicResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 发现
 */
public class FindFragment extends BaseFragment implements IFindContact.IFindView {

    FindPersenter persenter;
    @BindView(R.id.rv)
    RecyclerView rv;


    @Override
    protected void createPresenter() {
        persenter = new FindPersenter(this);
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

    }


}
