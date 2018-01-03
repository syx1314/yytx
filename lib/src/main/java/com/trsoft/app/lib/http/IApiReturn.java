package com.trsoft.app.lib.http;


/**
 * 处理API返回值
 * Created by huangzhe on 2017/4/13.
 */
public interface IApiReturn<T>{
     void run(ApiResultBean<T> apiResult);
     void error(String message);
}
