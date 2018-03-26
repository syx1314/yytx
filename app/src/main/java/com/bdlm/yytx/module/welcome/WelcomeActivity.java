package com.bdlm.yytx.module.welcome;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bdlm.yytx.MainActivity;
import com.bdlm.yytx.R;
import com.bdlm.yytx.base.SimpleBaseActivity;
import com.bdlm.yytx.entity.AppVersion;
import com.bdlm.yytx.entity.ImageBean;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DeviceUtils;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

public class WelcomeActivity extends SimpleBaseActivity implements WelcomeModel.WelResultListener {
    WelcomeModel model;
    /**
     * 高德定位需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private boolean gojump;
    private int flag;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.linBanner)
    RelativeLayout linBanner;
    private List<String> imgStr = new ArrayList<>();
    private boolean isFirst;
    private int i = 3;
    private static Handler handler = new Handler();

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new WelcomeModel();
        model.setListener(this);
        checkVersion();
        isFirst = PreferenceUtils.getInstance().getBoolean("isFirst");
        MyLog.e("isFirst--"+isFirst);
        if (!isFirst) {
            model.getAdvList("6");//欢迎页
            linBanner.setFitsSystemWindows(true);
        } else {
            PreferenceUtils.getInstance().saveData("isFirst", false);
            model.getAdvList("1");//轮播图
        }
    }

    @Override
    public void appInfo(final AppVersion appVersion) {

        if (!appVersion.getIs_update().equals("1")) {
            requestPermissoin("权限获得", "您禁止了权限可能影响应用的使用", "您禁止了权限,可能导致某些功能无法使用是否去开启", needPermissions);
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
                        requestPermissoin("权限获得", "您禁止了权限可能影响应用的使用", "您禁止了权限,可能导致某些功能无法使用是否去开启", needPermissions);
                    }

                }

            }
        });
    }

    @Override
    public void adList(final List<ImageBean> beanList) {
        for (ImageBean img : beanList) {
            imgStr.add(img.getAd_img());
        }

        if (!isFirst) {
            com.trsoft.app.lib.utils.ImageLoader.display(imgStr!=null?imgStr.get(0):"",linBanner);

            handler.postDelayed(runnable, 1000);
        }else {
            banner.setImages(imgStr);
            banner.isAutoPlay(false);
            //设置轮播时
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    com.trsoft.app.lib.utils.ImageLoader.display((String) path, imageView);
                }
            });
            banner.setDelayTime(1500);
            banner.start();
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == beanList.size() - 1) {
                    toActivity(MainActivity.class);
                }
            }
        });

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tvJump.setVisibility(View.VISIBLE);
            tvJump.setText(i + "  跳过");
            i--;
            if (i == 0) {
                handler.removeCallbacks(runnable);
                toActivity(MainActivity.class);
            }
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    public void error(String msg) {
        DialogUtil.showAlert(activity, msg, null);
        toActivity(MainActivity.class);
    }

    @OnClick(R.id.tv_jump)
    public void jump(View view) {
        handler.removeCallbacks(runnable);
        toActivity(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.cancelRequest();
        model = null;
        handler.removeCallbacks(runnable);

    }

    /**
     * 请求权限
     *
     * @param permission  权限
     * @param okTips      授权提示
     * @param noTips      拒绝权限
     * @param noAgainTips 点击不在提醒 提示用户如何操作
     */
    protected void requestPermissoin(final String okTips, final String noTips, final String noAgainTips, String... permission) {

        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permission).subscribe(new Subscriber<Permission>() {
            @Override
            public void onCompleted() {
                if (gojump) {
//                    toActivity(MainActivity.class);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Permission permission) {
                if (permission.granted) {
                    // 用户已经同意该权限
                    flag++;
                    if (flag == needPermissions.length - 1) {
                        gojump = true;
                    }

                } else if (permission.shouldShowRequestPermissionRationale) {
                    gojump = false;
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    DialogUtil.showAlert(activity, noTips, new CommonCallback<Boolean>() {
                        @Override
                        public void onCallBack(Boolean data) {
//                            toActivity(MainActivity.class);
                        }
                    });


                } else {
                    gojump = false;
                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                    DialogUtil.showAlertOkCancel(activity, noAgainTips, new CommonCallback<Boolean>() {
                        @Override
                        public void onCallBack(Boolean data) {
                            if (data) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 0);
                            } else {
//                                toActivity(MainActivity.class);
                            }
                        }
                    });
                }
            }
        });
    }

    private void checkVersion() {
        model.checkAppVersion(DeviceUtils.getVersionCode(activity));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
//            toActivity(MainActivity.class);
        }
    }
}
