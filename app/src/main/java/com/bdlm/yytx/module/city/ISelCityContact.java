package com.bdlm.yytx.module.city;

import com.bdlm.yytx.entity.CityBean;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/1/10.
 */

public interface ISelCityContact {

    interface  ICityListener extends IBaseListener{
        void cityData(List<CityBean> cityBeans);
    }

    interface ICityView extends IBaseView{
        void responseCityData(List<CityBean> cityBeans);
    }
    abstract class  ICityModel extends BaseModel{
       abstract void requestCityData(ICityListener listener);
    }
}
