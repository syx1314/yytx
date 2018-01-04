package com.trsoft.app.lib.entity;

import java.io.Serializable;

/**
 * 登录用户统一接口
 * Created by huangzhe on 2016/11/25.
 */

public interface ILoginConfig extends Serializable {
    String getToken();//URl的Token
}
