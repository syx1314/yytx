package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TicketBean;
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
        void scenicList(List<ScenicResponse> response);
        void reponseScenicDetails(ScenicDetailResponse response);
        void   responsePassportType(List<PassportTypeBean> passportTypeBeans);
        void responseTicket(List<TicketBean> ticketBeanList);
    }

    interface IScenicView extends IBaseView{
        void getScenicList(ScenicListResponse response);
        void getScenicList(List<ScenicResponse> response);
        void scenicDetails(ScenicDetailResponse response);
        void passportType(List<PassportTypeBean> passportTypeBeans);
    }
    interface ITicketView extends IBaseView{
        void resultTicketList(List<TicketBean> ticketBeanList);
    }

    abstract class IScenicModel extends BaseModel{
        abstract void requestScenicList(String longitude,String latitude,int passport_type,int city_id,int page,IScenicListener listener);
        abstract void  requestScenicDetails(String scenic_id,IScenicListener listener);
        abstract void  requestPassportType(IScenicListener listener);
        abstract void  requestSearchScenic(String search_name,IScenicListener listener);
        abstract void requestScenicList(String longitude,  String latitude,String scenic_id,IScenicListener listener);
        abstract void requestTicketList(IScenicListener listener);

    }
}
