package com.bdlm.yytx.entity;

/**
 * Created by Adim on 2018/1/1.
 */

public class LoginResponse {
    private String token;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
