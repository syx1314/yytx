package com.trsoft.app.lib.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.BaseAppActivity;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.R;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.rx.RxApiManager;
import com.trsoft.app.lib.view.CustomProgressDialog;

import java.io.File;
import java.io.IOException;


/**
 * 窗口工具类,提供可重用的窗口
 *
 * @author sszvip@qq.com
 * @copyright weibo.com/lostbottle
 */
public class DialogUtil {
    //region variable
    private static CustomProgressDialog progressDialogMy;
    private static ProgressDialog progDialog;
    private static Toast mToast;//为了实现疯狂模式下toast延时消失的问题
    private static Toast mToastCust;
    //endregion

    //region progress

    /**
     * 显示等待条
     *
     * @param ctx
     * @param msg
     */
    public static void showProgress(Activity ctx, String msg) {
        if (progDialog == null) {
            progDialog = new ProgressDialog(ctx);
        } else {
            if (progDialog.isShowing()) {
                return;
            }
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        //progDialog.setCancelable(false);//按返回取消
        progDialog.setCanceledOnTouchOutside(false);//点区域外不取消quxiao
        if (!Validator.isBlank(msg)) {
            progDialog.setMessage(msg);
        }
        progDialog.show();
    }

    /**
     * 显示等待条
     * Context == Activity
     */
    public static void showProgress(Context ctx, final boolean isKeyBack) {
        progressDialogMy = CustomProgressDialog.createDialog(ctx);
        progressDialogMy.setCanceledOnTouchOutside(false);//点区域外quxiao
        // 添加按键监听
        progressDialogMy.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                if (isKeyBack) {
                    if (arg1 == KeyEvent.KEYCODE_BACK) {
                        if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
                            progressDialogMy.cancel();
                        }
                    }
                }
                return false;
            }
        });
        progressDialogMy.show();
    }

    /**
     * 等待条用于访问HTTP，可取消访问
     *
     * @param activity
     * @param isKeyBack
     */
    public static void showProgressByApi(final BaseAppActivity activity, final boolean isKeyBack) {
        try {
            if (progressDialogMy == null) {
                progressDialogMy = CustomProgressDialog.createDialog(activity);
            } else {
                if (!progressDialogMy.isShowing()) {
                    progressDialogMy = CustomProgressDialog.createDialog(activity);
                } else {
                    return;
                }
            }
            if (progressDialogMy.isShowing()) {
                return;
            }
            progressDialogMy.setCanceledOnTouchOutside(false);//点区域外quxiao
            // 添加按键监听
            progressDialogMy.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                    if (isKeyBack) {
                        if (arg1 == KeyEvent.KEYCODE_BACK) {
                            if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
                                progressDialogMy.cancel();
                                RxApiManager.instance().cancel(activity.hashCode());
                            }
                        }
                    }
                    return false;
                }
            });
            progressDialogMy.show();
        } catch (Exception ex) {
            Logger.e(ex.getMessage(), ex);
        }
    }

    /**
     * 隐藏progress
     */
    public static void dismissProgress() {
        if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
            progressDialogMy.dismiss();
        }
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    //endregion

    //region toast

    /**
     * 显示并自动关闭
     *
     * @param act
     * @param msg
     */
    public static void showToastOnUIThread(final Activity act, final String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToastCust(act, msg);
            }
        });

    }

    /**
     * 此方法可以避免疯狂模式下点击按钮造成的长时间显示toast的问题
     *
     * @param ctx
     * @param msg
     */
    public static void showToastCust(Context ctx, String msg) {
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast_my, null);
            mToast.setView(toastRoot);
        }
        TextView message = (TextView) mToast.getView().findViewById(R.id.tv_toast);
        message.setText(msg);
        mToast.show();
    }

    public static void showToastCust(Context ctx, int resId) {
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast_my, null);
            mToast.setView(toastRoot);
        }
        TextView message = (TextView) mToast.getView().findViewById(R.id.tv_toast);
        message.setText(resId);
        mToast.show();
    }
    //endregion


    //region Dialog

    /**
     * 圆角提示框
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlert(Activity ctx, String msg, CommonCallback<Boolean> callback) {
        showAlert(ctx, ctx.getString(R.string.warn), msg, callback);
    }

    /**
     * 圆角提示框
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlert(final Activity ctx, String title, final String msg, final CommonCallback<Boolean> callback) {

        if (ctx == null) {
            return;
        }
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_warn, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(title);

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button btn = (Button) view.findViewById(R.id.alert_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCallBack(true);
                }
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setLayout(ctx.getResources().getDisplayMetrics().widthPixels / 100 * 70, mAlertDialog.getWindow().getAttributes().height);

    }

    /**
     * 圆角提示框
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlertNoTitle(final Activity ctx, final String msg, final CommonCallback<Boolean> callback) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_warn1, null);


        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button btn = (Button) view.findViewById(R.id.alert_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCallBack(true);
                }
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setLayout(ctx.getResources().getDisplayMetrics().widthPixels / 100 * 70, mAlertDialog.getWindow().getAttributes().height);

    }

    public static void showAlertYesNo(final Activity ctx, final String msg, final CommonCallback<Boolean> callback) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_ok, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(ctx.getString(R.string.warn));

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button yesBtn = (Button) view.findViewById(R.id.alert_yes);
        yesBtn.setText(ctx.getString(R.string.btn_yes));
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallBack(true);
            }
        });

        Button onBtn = (Button) view.findViewById(R.id.alert_no);
        onBtn.setText(ctx.getString(R.string.btn_no));
        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.onCallBack(false);
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setLayout(ctx.getResources().getDisplayMetrics().widthPixels / 100 * 70, mAlertDialog.getWindow().getAttributes().height);
    }

    public static void showAlertYesOrNoOnUIThread(final Activity ctx, final String msg, final CommonCallback callback) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlertYesNo(ctx, msg, callback);
            }
        });
    }

    public static void showAlertOkCancel(final Activity ctx, final String msg, final CommonCallback<Boolean> callback) {
        showAlertCusTitlel(ctx, ctx.getString(R.string.warn), msg, ctx.getString(R.string.btn_ok), ctx.getString(R.string.btn_cancel), callback);
    }

    /**
     * 自定义
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlertCusTitlel(final Activity ctx, final String title, final String msg, final String okName, final String cancelName, final CommonCallback<Boolean> callback) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_ok, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(title);

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button yesBtn = (Button) view.findViewById(R.id.alert_yes);
        yesBtn.setText(ctx.getString(R.string.btn_ok));
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallBack(true);
                mAlertDialog.dismiss();
            }
        });

        Button onBtn = (Button) view.findViewById(R.id.alert_no);
        onBtn.setText(ctx.getString(R.string.btn_cancel));
        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.onCallBack(false);
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * 自定义
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlertCusTitle(final Activity ctx, final String title, final String msg, final String okName, final String cancelName, final CommonCallback<Boolean> callback) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_ok, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(title);

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button yesBtn = (Button) view.findViewById(R.id.alert_yes);
        yesBtn.setText(okName);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallBack(true);
                mAlertDialog.dismiss();
            }
        });

        Button onBtn = (Button) view.findViewById(R.id.alert_no);
        onBtn.setText(cancelName);
        onBtn.setTextColor(Color.BLACK);
        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.onCallBack(false);
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * 自定义
     *
     * @param ctx
     * @param msg
     * @param callback
     */
    public static void showAlertCusTitlel(final Activity ctx, final String title, final String msg, final CommonCallback<Boolean> callback) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_ok, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(title);

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button yesBtn = (Button) view.findViewById(R.id.alert_yes);
        yesBtn.setText(ctx.getString(R.string.btn_cancel));
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallBack(false);
                mAlertDialog.dismiss();
            }
        });

        Button onBtn = (Button) view.findViewById(R.id.alert_no);
        onBtn.setText(ctx.getString(R.string.btn_ok));
        onBtn.setTextColor(Color.WHITE);
        onBtn.setBackgroundColor(0xffff6600);
        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.onCallBack(true);
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * 自定义
     *
     * @param ctx
     * @param msg
     * @param
     */
    public static void showAlertCusTitlel(final Activity ctx, final String title, final String msg, final String okName) {
        final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
        mAlertDialog.setCancelable(false);

        View view = ctx.getLayoutInflater().inflate(R.layout.alert_dialog_ok, null);
        TextView tv = (TextView) view.findViewById(R.id.alert_id);
        tv.setText(title);

        TextView tc = (TextView) view.findViewById(R.id.alert_context);
        tc.setText(msg);

        Button yesBtn = (Button) view.findViewById(R.id.alert_yes);
        yesBtn.setText(ctx.getString(R.string.btn_ok));
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

        Button onBtn = (Button) view.findViewById(R.id.alert_no);
        onBtn.setText(ctx.getString(R.string.btn_cancel));
        onBtn.setVisibility(View.GONE);
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }
    //endregion

    public static void showAlertMenuCust(final Activity ctx, String title, final String[] itemList, final Integer selectIndex, final CommonCallback<Integer> callback) {
        try {
            final Dialog mAlertDialog = new Dialog(ctx, R.style.MyDialogStyle);
            //内容
            ListAdapter mAdapter = new ArrayAdapter(ctx, R.layout.alertdialog_item, itemList);
            LayoutInflater inflater = LayoutInflater.from(ctx);
            View view = inflater.inflate(R.layout.alertdialog, null);
            view.setTranslationX(mAlertDialog.getWindow().getAttributes().width);
            //标题
            TextView titleView = (TextView) view.findViewById(R.id.tv_title);
            if (Validator.isNotEmpty(title)) {
                titleView.setText(title);
                titleView.setVisibility(View.VISIBLE);
            } else {
                titleView.setVisibility(View.GONE);
            }
            //取消
            TextView btn = (TextView) view.findViewById(R.id.alert_cancel);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.cancel();
                }
            });
            final ListView listview = (ListView) view.findViewById(android.R.id.list);
            listview.setAdapter(mAdapter);
            if (selectIndex != null) {
                listview.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) listview.getChildAt(selectIndex);
                        tv.setTextColor(Color.parseColor("#fdb12c"));
                    }
                });
            }
            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                    callback.onCallBack(pos);
                    mAlertDialog.cancel();
                }
            });

            mAlertDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
            mAlertDialog.getWindow().setWindowAnimations(R.style.alertTranStyle);
            mAlertDialog.show();
            mAlertDialog.getWindow().setContentView(view);

            mAlertDialog.getWindow().setLayout(ctx.getResources().getDisplayMetrics().widthPixels, mAlertDialog.getWindow().getAttributes().height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param msg
     */
    public static void showToastCust(String msg) {
        showToastCust(BaseApplication.mContext, msg);
    }
}