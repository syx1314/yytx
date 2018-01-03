package com.trsoft.app.lib.entity;

import java.io.Serializable;

/**
 * 登录用户统一接口
 * Created by huangzhe on 2016/11/25.
 */

public interface ILoginConfig extends Serializable {
    String getUserId();//用户id
    String getName();//用户名称
    String getPhone();//用户手机
    String getPassword();
    String getToken();//URl的Token
}
