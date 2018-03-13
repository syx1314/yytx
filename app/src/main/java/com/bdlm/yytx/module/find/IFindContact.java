package com.bdlm.yytx.module.find;

import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TourInfoListBean;
import com.bdlm.yytx.entity.TourInfoListResponse;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/2/7.
 */

public interface IFindContact {

    interface IFindListener extends IBaseListener {
        void resultScenic(List<ScenicResponse> responses);

        void resultSortScenic(List<ScenicPlaySortBean> responses);
        //旅游资讯列表
        void resultTourInfoList(TourInfoListResponse tourInfoList);
    }

    interface IFindView extends IBaseView {
        void resultScenic(List<ScenicResponse> responses);

        /**
         * 游玩排行
         *
         * @param responses
         */
        void resultSortScenic(List<ScenicPlaySortBean> responses);
    }

    interface ITourInfoListView extends IBaseView {
        void resultTourInfoList(TourInfoListResponse tourInfoList);
    }

    abstract class BaseFindModel extends BaseModel {
        protected abstract void requestRecommendScenic(IFindListener listener);

        /**
         * 游玩排行
         *
         * @param listener
         */
        protected abstract void requestSortScenic(IFindListener listener);

        protected  abstract void requestTourInfoList(String page,IFindListener listener);
    }

}
