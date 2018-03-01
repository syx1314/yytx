package com.bdlm.yytx.constant;

/**
 * Created by yyj on 2018/3/1.
 */

public enum  BussinessTypeEnum {
    TRAVEL(1,"旅行社"),EAT(2,"我要吃"),HOTEL(3,"我要住"),PLAY(4,"我要玩");

    private  int code;
    private  String name;

    BussinessTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BussinessTypeEnum getStatus(int code) {
        for(BussinessTypeEnum bussinessTypeEnum:BussinessTypeEnum.values()){
            if(bussinessTypeEnum.code==code){
                return bussinessTypeEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
