package com.bdlm.yytx.base;


import com.bdlm.yytx.constant.Constant;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.entity.ILoginConfig;
import com.trsoft.app.lib.utils.PreferenceUtils;


/**
 * Created by 小呆呆 on 2017/12/20 0020.
 */

public class MyApplication extends BaseApplication {
    @Override
    public String getApiUrl() {
        return Constant.BASEURL;
    }

    @Override
    public void quit(boolean isClearData) {

    }

    @Override
    public ILoginConfig getLoginConfig() {
        return new ILoginConfig() {
            @Override
            public String getToken() {
                return PreferenceUtils.getInstance().getString(Constant.TOKEN);
            }
        };
    }

    @Override
    public Class getLoginActivityClass() {
        return null;
    }

    @Override
    public void saveLoginConfig(ILoginConfig mLoginConfig) {

    }


}
