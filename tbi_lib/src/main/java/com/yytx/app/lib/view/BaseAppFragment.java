package com.yytx.app.lib.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yytx.app.lib.BaseApplication;

import org.xutils.x;

/**
 * Fragment基类
 * Created by huangzhe on 2016/11/25.
 */

public abstract class BaseAppFragment extends Fragment {
    /**
     * 初始化
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 激活显示
     */
    public abstract void onActivate();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    protected ActivitySupport ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//启用onCreateOptionMenu
        ctx = (ActivitySupport) getActivity();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//   	 setUIStatus();
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public BaseApplication getApplication() {
        return (BaseApplication) ctx.getApplication();
    }



    /**
     * 添加碎片
     *
     * @param fragmentId
     * @param fragment
     * @param tag
     * @param isHidden   是否隐藏
     */
    protected void addFragment(int fragmentId, Fragment fragment, String tag, boolean isHidden) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragmentId, fragment, tag);
        if (isHidden) {
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    /**
     * 显示Fragment
     *
     * @param tag
     */
    public void showFragment(String tag) {
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (this.getChildFragmentManager().getFragments() != null) {
            for (Fragment fragment : this.getChildFragmentManager().getFragments()) {
                transaction.hide(fragment);
            }
        }
        BaseAppFragment baseAppFragment = (BaseAppFragment) this.getChildFragmentManager().findFragmentByTag(tag);
        transaction.show(baseAppFragment);
        baseAppFragment.onActivate();//调用激活
        transaction.commit();
    }
}

