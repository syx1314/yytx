package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.entity.ScenicDetailResponse;
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
        void reponseScenicDetails(ScenicDetailResponse response);
    }
    interface IScenicView extends IBaseView{
        void getScenicList(ScenicListResponse response);
        void scenicDetails(ScenicDetailResponse response);
    }

    abstract class IScenicModel extends BaseModel{
        abstract void requestScenicList(double longitude,double latitude,int passport_type,int city_id,int page,IScenicListener listener);
        abstract void  requestScenicDetails(String scenic_id,IScenicListener listener);
    }
}
