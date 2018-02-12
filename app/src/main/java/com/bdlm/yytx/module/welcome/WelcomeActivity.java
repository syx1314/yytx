package com.bdlm.yytx.module.welcome;

import android.support.annotation.Nullable;
import android.os.Bundle;

import com.bdlm.yytx.MainActivity;
import com.bdlm.yytx.R;
import com.bdlm.yytx.base.SimpleBaseActivity;
import com.bdlm.yytx.entity.AppVersion;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DeviceUtils;
import com.trsoft.app.lib.utils.DialogUtil;

public class WelcomeActivity extends SimpleBaseActivity implements WelcomeModel.WelResultListener {
    WelcomeModel model;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new WelcomeModel();
        model.checkAppVersion(DeviceUtils.getVersionCode(activity));
        model.setListener(this);
    }

    @Override
    public void appInfo(final AppVersion appVersion) {
        if(!appVersion.getIs_update().equals("1")){
            try {
                Thread.sleep(2000);
                toActivity(MainActivity.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        DialogUtil.showAlertCusTitle(activity, "版本提示", appVersion.getExplain(), "更新", "忽略", new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {

                if (data) {
                    toUrl(appVersion.getDownurl());
                } else {
                    if (appVersion.getIs_forced_update().equals("1")) {
                        finish();
                    } else {
                        toActivity(MainActivity.class);
                    }

                }

            }
        });
    }

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
        toActivity(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.cancelRequest();
        model = null;
    }
}
