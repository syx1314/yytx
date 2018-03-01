package com.bdlm.yytx.entity;

import java.util.List;

/**
 * Created by yyj on 2018/3/1.
 */

public class BusinessListResponse {
    private Page pageInfo;
    private List<BusinessBean> partnerList;

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessBean> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<BusinessBean> partnerList) {
        this.partnerList = partnerList;
    }
}
