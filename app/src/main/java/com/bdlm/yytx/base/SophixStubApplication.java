package com.bdlm.yytx.base;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.trsoft.app.lib.utils.DeviceUtils;
import com.trsoft.app.lib.utils.MyLog;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = DeviceUtils.getVersionCode(this);
        String appID = "24816833-1";
        String appSecret = "2832a391c6f95988b811f38dd3b84b0f";
        String rsa = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDtsJgEQh25dX4+FYYBQ3vbCTFd+7gKAdC+KqMrpuHhUSA9XaH3LS94Ef0/QrsxNU4owUVOBDXTILmpyjeTIrvZd6RIOQfQJ6a4XeRiXFYdqJpUx/vPoAt6yQR1EoP3Hgt3Kfrp4ZfF1kn455aBNnVdWfGvs6iTff8ItuCf1+ziTYuTrAbPEBtVIt/ODsw0W99ox6RQL/aqciSapdx1BdL1utkR/ZYEySsGhn+qcDHVvHkN14rkyo/4JxsIYKz8g1fY8Cuo/8ItpynCQ1Ww1ETnMtozYN0h1XtnuOyvEPjCEv2jpvyIImoZTYEZu9iOURRl0NT3qDiory5Gp7HtJSPtAgMBAAECggEBAIg64MYABv1WxAZdW7K5tPEzcaIGNdM2eyejVGYeffQgBTRmx//dXAmbdeSeIJ/xh1yAeXJfo2HgrckM55FjJPtdQUFLvpilQT8GQXHGrmzNR7cz77Vua2XydKWM+SnXhIbF/tOxwsVXLoqEYZSpW9cnSAiGFiaC3ntmJZQ3s8N3x5SmTQIhGDlr92KnUHPhY5xQX9nGc9e3md+Hu29XLafs7JcOT17ffPtqgQP5ZnVoPPcsxf35btbeiWQbgO5wRSit32S2g0BXRSVowObUQ44Tb1CL8O6eyj3yZyE8m/uxJKz0KneqeTWloEwScFVuPBC8+LuJ2ouK5o3TG9GFngECgYEA/k9D1yuCU3d72RCQhaVW8Xet0Yj2RM5qn2c62NKxey5oQlNfguDzuKpnxSL6J/MleBxSQ9GPk78qjDSczNRu8jppaz96nm4sf3MDX6tBF7QC4s2As2Eo22durrReiSaYl6Y3ogp2zdWarWrsKcSJZJEL0BAv3pKKC0qcHXw2lEECgYEA70UMZgzS4u7d6Xv6mDZvDHaS4JlNGA/ifPzGXHjqOGgYCoLXHeL4+7KVbDQNKQokRU5Opq0qu1aD3nsvpbHIjtUR3fWzKE73jduC7VCF0NXZvfwzfeDKVZJr4CW3s+jBzsFwqn2+59itbfHUMRl5tV9K1hexH6RKcYitLOW19K0CgYEAo7TK6xxrDOgBpb3wCthgm5h7IEOWpLxsDqkZTLJ05eL94pRhC5Nb0SiBHHpjpovzNtqkalvgnS+WaCMGPSUTNoM7kXqPqKUrp++V4GQRxvxuTyoTC2YkMvjmWzhpEsjxuc2aSomJ6P70T2ZhGJIYD9D/VbTV2+VhT9t7m892IwECgYBXvCrqtKMTgWEvvz1JWRRpLoV3A9+IBjMXf5zAxbmky06ddBKrK9H+gSJXVdLJjKV2IFuotHCp1vRtvR/gkwsmjKyLhg+WpTYS9z3QBSm8RN4d0v+Slb27mQAjZdExkBSY2seUTJ2W6BXX7cfP5TaCshl/3ZoeLyD49pQBlY2d1QKBgQDOYe+kDEbOmaw4Jh2mlC1z0WiA094xxD53xs51FU57Z6oD77iuma6Rkq5TAFcJvvTsO9nqMtNJqIpWq/biY/Ymp7+2yFMgSYsg+307RD6HiMPuSFiUUlIt69ZfABr6mwVX+yzVp//05beN4C7KzbrpteIeUPmXW2p2tiNL5IdYIA==";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(appID, appSecret, rsa)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            //// 表明补丁加载成功
                            MyLog.e("sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            MyLog.e( "sophix preload patch success. restart app to make effect.");
                        }else{
                            MyLog.e( "状态码----"+code);
                        }
                    }
                }).initialize();
    }
}