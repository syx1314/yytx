package com.yytx.app.lib.entity;

import java.io.Serializable;

/**
 * 传输类实体返回基类
 * 适用于HTTP请求后，返回状态信息
 * Created by huangzhe on 2016/11/25.
 */

public class BaseBeanResult<T> extends BaseBean {
    private T data;
    private int  code ;//是否访问成功
    private String  msg;//返回消息
    private BaseBeanResult() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
