package com.bdlm.yytx.module.business;

import com.bdlm.yytx.entity.BusinessBean;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

/**
 * Created by yyj on 2018/2/2.
 */

public interface BusinessContact {


    interface IBusinessListener extends IBaseListener {
        void submitApprove(String msg);
    }

    interface IBusinessView extends IBaseView {
        void resultApprove(String msg);
    }

    abstract class BaseBussinessModel extends BaseModel {
        abstract void submitApprove(BusinessBean businessBean, IBusinessListener listener);
    }
}
