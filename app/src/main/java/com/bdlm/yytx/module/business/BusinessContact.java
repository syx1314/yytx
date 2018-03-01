package com.bdlm.yytx.module.business;

import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.BusinessListResponse;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseView;

import java.io.File;
import java.util.List;

/**
 * Created by yyj on 2018/2/2.
 */

public interface BusinessContact {


    interface IBusinessListener extends IBaseListener {
        void submitApprove(String msg);
        void managerType(List<ManagerTypeBean> managerTypeBeanList);
        void uploadFile(UploadPicRespon respon);
        void bussinessList(BusinessListResponse response);
    }

    interface IBusinessView extends IBaseView {
        void resultApprove(String msg);
        void resultManagerType(List<ManagerTypeBean> managerTypeBeanList);
        void reultUploadFile(UploadPicRespon respon);
    }

    interface IBussinessListView extends IBaseView{
        void  resultBussinessList(BusinessListResponse response);
    }
    abstract class BaseBussinessModel extends BaseModel {
        abstract void submitApprove(BusinessBean businessBean, IBusinessListener listener);
        abstract void requestManagerType( IBusinessListener listener);
        abstract void uploadFile(String type, File file,IBusinessListener listener);
        abstract void requestBussinessList(String manage_type,String longitude,String latitude,String page,IBusinessListener listener);
    }
}
