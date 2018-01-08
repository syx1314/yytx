package com.trsoft.app.lib.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.trsoft.app.lib.BaseApplication;

/**
 * Created by yyj on 2018/1/4.
 */

public class ImageLoader {

    public static void display( String url, ImageView imageView) {

        Glide.with(BaseApplication.mContext).load(url).into(imageView);
    }
    public static void display(String url, final View view) {

        Glide.with(BaseApplication.mContext).load(url).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        view.setBackground(resource.getCurrent());
            }
        });
    }

    public static void displayCircleImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).transform(new GlideCircleTransform(activity)).into(imageView);
    }
}
