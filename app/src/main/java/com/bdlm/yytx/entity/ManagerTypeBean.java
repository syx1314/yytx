package com.bdlm.yytx.entity;

/**
 * 经营类型
 * Created by yyj on 2018/2/27.
 */

public class ManagerTypeBean {

    private String id;
    private String type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return type_name;
    }
}