package com.bdlm.yytx.module.find;

import com.bdlm.yytx.entity.PositionBean;
import com.bdlm.yytx.entity.ScenicPlaySortBean;
import com.bdlm.yytx.entity.ScenicResponse;
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

    abstract class BaseFindModel extends BaseModel {
        protected abstract void requestRecommendScenic(IFindListener listener);

        /**
         * 游玩排行
         *
         * @param listener
         */
        protected abstract void requestSortScenic(IFindListener listener);
    }

}
