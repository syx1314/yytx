package com.yytx.app.lib.util.ui;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 * Created by huangzhe on 2017/5/12.
 */

public class AnimationUtil {
    /**
     * 控件从自身位置的最右端开始向左水平滑动了自身的宽度，持续时间为0.2s
     * @param view
     */
    public static void showScrollAnim(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation mShowAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);
        view.startAnimation(mShowAction);
    }

    /**
     * 控件从自身位置的最左端开始水平向右滑动隐藏动画，持续时间0.2s
     * @param view
     */
    public static void hiddenScrollAnim(View view) {
        view.setVisibility(View.GONE);
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(200);
        view.startAnimation(mHiddenAction);
    }

    /**
     * 控件以自身中心为圆心旋转X度，持续时间为0.2s
     * @param view
     */
    public static void rotateAnim(View view,float fromDegrees, float toDegrees,long durationMillis)
    {
        view.setVisibility(View.VISIBLE);
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(durationMillis);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 控件由原来大小尺寸沿自身尺寸中心逐渐缩放到0，持续时间为0.2s
     * @param view
     */
    public static void toHideAnim(View view)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);
        view.startAnimation(scaleAnimation);
    }

    /**
     * 控件由完全透明到完全不透明变化，持续时间为0.2s
     * @param view
     */
    private void toVisibleAnim(View view)
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(200);
        view.startAnimation(alphaAnimation);
    }
}
