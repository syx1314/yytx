package com.bdlm.yytx.module.scenic;

import com.bdlm.yytx.R;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.PassportTypeBean;
import com.bdlm.yytx.entity.ScenicDetailResponse;
import com.bdlm.yytx.entity.ScenicListResponse;
import com.bdlm.yytx.entity.ScenicResponse;
import com.bdlm.yytx.entity.TicketBean;
import com.trsoft.app.lib.mvp.BasePresenter;
import com.trsoft.app.lib.mvp.IBaseView;

import java.util.List;

/**
 * Created by yyj on 2018/1/5.
 */

public class ScenicPresenter extends BasePresenter<IBaseView> implements ScenicContact.IScenicListener {
    private ScenicModel scenicModel;
    private ScenicContact.IScenicView scenicView;
    private ScenicContact.ITicketView ticketView;

    public ScenicPresenter(IBaseView view) {
        scenicModel = new ScenicModel();
        attachV(view);
    }

    @Override
    public void attachV(IBaseView view) {
        super.attachV(view);
        if (view instanceof ScenicContact.IScenicView) {
            scenicView = (ScenicContact.IScenicView) view;
        } else if (view instanceof ScenicContact.ITicketView) {
            ticketView = (ScenicContact.ITicketView) view;
        }
    }

    public void requestScenicList(String longitude, final String latitude, int passport_type, int city_id, int page) {
        scenicModel.requestScenicList(longitude, latitude, passport_type, city_id, page, this);
    }

    public void requestScenicDetails(String scenic_id) {
        scenicModel.requestScenicDetails(scenic_id, this);
    }

    public void requestScenicSearch(String search_name) {
        scenicModel.requestSearchScenic(search_name, this);
    }


    public void requestPassportType() {
        scenicModel.requestPassportType(this);
    }

    public void requestScenicList(String longitude, String latitude, String scenic_id) {
        if (longitude != null & latitude != null) {
            scenicModel.requestScenicList(longitude, latitude, null, this);
        } else {
            scenicModel.requestScenicList(null, null, scenic_id, this);
        }
    }

    public void requestTicketList() {
        scenicModel.requestTicketList(this);
    }

    public void cancelRequest() {
        scenicModel.cancelRequest();
    }

    @Override
    public void error(String msg) {
        if (scenicView != null)
            scenicView.error(msg);
    }

    @Override
    public void scenicList(ScenicListResponse response) {

        if (scenicView != null)
            scenicView.getScenicList(response);
    }

    @Override
    public void scenicList(List<ScenicResponse> response) {
        if (scenicView != null) {
            scenicView.getScenicList(response);
        }
    }

    @Override
    public void reponseScenicDetails(ScenicDetailResponse response) {
        if (scenicView != null) {
            scenicView.scenicDetails(response);
        }
    }

    @Override
    public void responsePassportType(List<PassportTypeBean> passportTypeBeans) {
        if (scenicView != null) {
            scenicView.passportType(passportTypeBeans);
        }
    }

    @Override
    public void responseTicket(List<TicketBean> ticketBeanList) {
        if (ticketView != null)
            ticketView.resultTicketList(ticketBeanList);
    }
}
