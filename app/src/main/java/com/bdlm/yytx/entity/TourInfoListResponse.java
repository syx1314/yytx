package com.bdlm.yytx.entity;

import java.util.List;

/**
 * 旅游资讯列表 返回
 * Created by yyj on 2018/3/13.
 */

public class TourInfoListResponse {
    private Page pageInfo;
    private List<TourInfoListBean> tourInfoList;

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<TourInfoListBean> getTourInfoList() {
        return tourInfoList;
    }

    public void setTourInfoList(List<TourInfoListBean> tourInfoList) {
        this.tourInfoList = tourInfoList;
    }
}
