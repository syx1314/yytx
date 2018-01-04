package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.entity.ScenicListResponse;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/1/4.
 */

public interface ScenicContact {

    interface  IScenicListener extends IBaseListener{
        void scenicList(ScenicListResponse response);
    }
    interface IScenicView extends IBaseView{
        void getScenicList(double longitude,double latitude,int passport_type,int city_id,int page);
    }

    abstract class IScenicModel extends BaseModel{
        abstract void requestScenicList(double longitude,double latitude,int passport_type,int city_id,int page,IScenicListener listener);
    }
}
