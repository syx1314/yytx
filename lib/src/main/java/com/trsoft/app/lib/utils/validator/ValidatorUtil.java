package com.trsoft.app.lib.utils.validator;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trsoft.app.lib.ActivitySupport;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;


/**
 * 验证工具类
 * Created by huangzhe on 2017/4/18.
 */
public class ValidatorUtil {
    /**
     * 必填验证
     * @param activitySupport
     * @param editTexts
     * @return
     */
    public static boolean RequiredByEditText(ActivitySupport activitySupport, EditText... editTexts){
        for(EditText editText:editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                if(editText.getHint() != null) {
                    DialogUtil.showAlert(activitySupport, editText.getHint().toString(), null);
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 必填验证
     * @param activitySupport
     * @param editText
     * @return
     */
    public static boolean RequiredByEditText(ActivitySupport activitySupport, EditText editText,String msg){
        if (TextUtils.isEmpty(editText.getText())) {
            DialogUtil.showAlert(activitySupport, msg, null);
            return false;
        }
        return true;
    }

    /**
     * TextView
     * @param tv
     * @param val
     */
    public static void setTextVal(TextView tv,String val){
        if(Validator.isNotEmpty(val)){
            tv.setText(val);
        }else{
            tv.setText("");
        }
    }

    /**
     * 设置值，并且移除整体控件
     * @param tv
     * @param val
     * @param layout
     */
    public static void setTextValOrRemove(TextView tv, String val, LinearLayout layout) {
        if (Validator.isNotEmpty(val)) {
            tv.setText(val);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    /**
     * 输入框都验证通过了按钮可见
     * @param submitBtn 提交按钮
     * @param editTexts 输入组
     * @param validatorResult 成功回调方法[可做验证]
     */
    public static void setBtnEnable(final Button submitBtn, final IValidatorResult validatorResult, final EditText... editTexts){
        submitBtn.setEnabled(false);
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(EditText editText:editTexts){
                    if(TextUtils.isEmpty(editText.getText())){
                        submitBtn.setEnabled(false);
                        return;
                    }
                }
                if(validatorResult!=null){
                    //自定义验证
                    if(validatorResult.validator()){
                        submitBtn.setEnabled(true);
                    }
                }else{
                    submitBtn.setEnabled(true);
                }
            }
        };
        for(EditText editText:editTexts){
            editText.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 得到EditView的值
     * @param editText
     * @return
     */
    public static String getEditView(EditText editText){
        if(!TextUtils.isEmpty(editText.getText())){
            return editText.getText().toString();
        }
        return null;
    }
}
