package com.bdlm.yytx.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bdlm.yytx.R;
import com.trsoft.app.lib.utils.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 小呆呆 on 2017/11/25 0025.
 */

public class CommonTitle extends LinearLayout {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Context context;
    private IClickFun clickFun;

    public IClickFun getClickFun() {
        return clickFun;
    }

    public void setClickFun(IClickFun clickFun) {
        this.clickFun = clickFun;
    }

    public CommonTitle(Context context) {
        super(context);
        init(context, null);
    }

    public CommonTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitle);
        int color = typedArray.getColor(R.styleable.CommonTitle_commonTitleColor, getResources().getColor(R.color.red));
        Drawable titleDrawable = typedArray.getDrawable(R.styleable.CommonTitle_title_icon);
        boolean leftVisibility = typedArray.getBoolean(R.styleable.CommonTitle_leftVisibility, true);
        boolean rightVisibility = typedArray.getBoolean(R.styleable.CommonTitle_rightVisibility, false);
        boolean rightTvVisibility = typedArray.getBoolean(R.styleable.CommonTitle_rightTvVisibility, false);
        String rightTvContent = typedArray.getString(R.styleable.CommonTitle_rightTvContent);
        String title = typedArray.getString(R.styleable.CommonTitle_title);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_comon_title, null);
        View rlTitle = view.findViewById(R.id.rl_title);
        rlTitle.setBackgroundColor(color);
        addView(view);
        ButterKnife.bind(this);
        if (titleDrawable != null) {
            titleDrawable.setBounds(10, 0, 40, 40);
            tvTitle.setCompoundDrawables(null, null, titleDrawable, null);
        }
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (leftVisibility) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.INVISIBLE);
        }
        if (rightVisibility) {
            ivRight.setVisibility(View.VISIBLE);
        } else {
            ivRight.setVisibility(INVISIBLE);
        }


            if (!TextUtils.isEmpty(rightTvContent)) {
                tvRight.setText(rightTvContent);
                tvRight.setVisibility(VISIBLE);
            } else {
                tvRight.setVisibility(INVISIBLE);
            }


    }


    @OnClick({R.id.iv_left, R.id.iv_right, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ((Activity) context).onBackPressed();
                break;
            case R.id.iv_right:
                clickFun.rightOclick();
                break;
            case R.id.tv_right:
                if(clickFun!=null)
                clickFun.rightOclick();
                break;
        }
    }


    public interface IClickFun {
        void leftOclick();

        void rightOclick();
    }

    public void setTvTitle(String title) {
        if(Validator.isNotEmpty(title)) {
            tvTitle.setText(title);
        }else {
            tvTitle.setText("");
        }
    }
}
