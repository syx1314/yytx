package com.yytx.app.lib.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import com.yytx.app.lib.core.IBaseConst;

import java.util.Timer;
import java.util.TimerTask;

import yytx.com.android.lib.R;


/**
 * 自定义提示框
 * Created by huangzhe on 2016/11/25.
 */

public class CustomProgressDialog extends Dialog {
    private Timer timer;

    public CustomProgressDialog(Context context){
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        }, IBaseConst.BASE.DIALOG_AUTO_CLOSE_TIME);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState);
    }

    public static CustomProgressDialog createDialog(Context context){
        CustomProgressDialog  customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.dlg_loading);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.getWindow().getAttributes().y = -100;
        customProgressDialog.setCanceledOnTouchOutside(false);
        return customProgressDialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(timer!=null) {
            timer.cancel();
        }
    }
}
