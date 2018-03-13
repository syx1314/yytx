package com.bdlm.yytx.module.home;

import com.bdlm.yytx.api.IScenicApi;
import com.bdlm.yytx.entity.HomeURLBean;
import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.module.scenic.ScenicContact;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/1/12.
 */

public interface IHomeContact {

    interface IHomeListener extends IBaseListener {

        void resultScenic(List<ScenicResponse> responses);
        void notice(String noticeStr);
        void resultTourGoodsUrl(HomeURLBean urlBean);
    }

    interface  IHomeView extends IBaseView{
        void resultPosition(PositionBean positionBean);
        void resultScenic(List<ScenicResponse> responses);
        void resultNotice(String noticeStr);
        void resultTourGoodsUrl(HomeURLBean urlBean);
    }


}
