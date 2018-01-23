package com.bdlm.yytx.entity;

import android.graphics.pdf.PdfDocument;

import java.util.List;

/**
 * Created by Adim on 2018/1/23.
 */

public class TicketListResponse {
    private Page pageInfo;
    private List<TicketBean> senicList;

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<TicketBean> getSenicList() {
        return senicList;
    }

    public void setSenicList(List<TicketBean> senicList) {
        this.senicList = senicList;
    }
}
