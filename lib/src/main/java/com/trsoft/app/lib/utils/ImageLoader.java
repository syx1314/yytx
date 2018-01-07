package com.trsoft.app.lib.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trsoft.app.lib.BaseApplication;

/**
 * Created by yyj on 2018/1/4.
 */

public class ImageLoader {

    public static void display( String url, ImageView imageView) {

        Glide.with(BaseApplication.mContext).load(url).into(imageView);
    }

    public static void displayCircleImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).transform(new GlideCircleTransform(activity)).into(imageView);
    }
}
