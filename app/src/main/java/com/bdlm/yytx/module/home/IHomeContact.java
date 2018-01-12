package com.bdlm.yytx.module.home;

import com.bdlm.yytx.entity.PositionBean;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

/**
 * Created by yyj on 2018/1/12.
 */

public interface IHomeContact {

    interface IHomeListener extends IBaseListener {
        //定位结果
        void resultPosition(PositionBean positionBean);
    }

    interface  IHomeView extends IBaseView{
        void resultPosition(PositionBean positionBean);
    }
}
