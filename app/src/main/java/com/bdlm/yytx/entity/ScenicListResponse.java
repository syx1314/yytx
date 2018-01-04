package com.bdlm.yytx.entity;

import java.util.List;

/**
 * Created by yyj on 2018/1/4.
 */

public class ScenicListResponse {
    private Page pageInfo;
    private List<ScenicResponse> senicList;

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<ScenicResponse> getSenicList() {
        return senicList;
    }

    public void setSenicList(List<ScenicResponse> senicList) {
        this.senicList = senicList;
    }
}
