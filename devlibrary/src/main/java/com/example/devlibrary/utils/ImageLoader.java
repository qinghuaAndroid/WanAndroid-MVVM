package com.example.devlibrary.utils;


import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.devlibrary.R;
import com.example.devlibrary.app.App;
import com.example.devlibrary.transform.GlideCircleBorderTransform;
import com.example.devlibrary.transform.GlideRoundedCornersTransform;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImageLoader {
    private static final String STYLE_CIRCLE = "circle";
    private static final String STYLE_ROUND = "round";
    private static RequestOptions mOptionAvator;

    private static int defImage = 0;
    private static int defHeader = R.mipmap.ic_default_avatar;
    private static String mStyle = STYLE_ROUND;

    public static void loadAvator(ImageView iv, int res) {
        loadAvator(iv, res, defHeader);
    }

    public static void load(ImageView iv, String url) {
        load(iv, url, defImage);
    }

    public static void load(ImageView iv, int res) {
        load(iv, res, defImage);
    }

    public static void loadAvator(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(getOptionAvator())
                .into(iv);
    }

    public static void loadAvator(ImageView iv, int res, int defaultPicId) {
        Glide.with(iv.getContext())
                .load(res)
                .transition(new DrawableTransitionOptions().crossFade(new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .apply(getOptionAvator().placeholder(defaultPicId))
                .into(iv);
    }

    public static void load(ImageView iv, String url, int defaultPicId) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(defaultPicId).error(defaultPicId))
                .into(iv);
    }

    public static void load(ImageView iv, int res, int defaultPicId) {
        iv.setTag(null);//需要清空tag，否则报错
        Glide.with(iv.getContext())
                .load(res)
                .apply(new RequestOptions().placeholder(defaultPicId))
                .into(iv);
    }


    public static void load(ImageView iv, String url, RequestOptions requestOptions) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    public static void load(ImageView iv, int res, RequestOptions requestOptions) {
        Glide.with(iv.getContext())
                .load(res)
                .apply(requestOptions)
                .into(iv);
    }


    public synchronized static RequestOptions getOptionAvator() {
        if (mOptionAvator == null) {
            initHeaderOption();
        }

        return mOptionAvator;
    }

    public synchronized static RequestOptions getOptionAvator(String style) {
        mStyle = style;
        if (mOptionAvator == null) {
            initHeaderOption();
        }

        return mOptionAvator;
    }

    private static void initHeaderOption() {
        if (STYLE_CIRCLE.equals(mStyle)) {
            mOptionAvator = RequestOptions
                    .bitmapTransform(new GlideCircleBorderTransform(10, Color.WHITE))
                    .placeholder(defHeader)
                    .error(defHeader);
        } else {
            mOptionAvator = new RequestOptions()
                    .optionalTransform(new GlideRoundedCornersTransform(App.sContext, 8f, GlideRoundedCornersTransform.CornerType.ALL))
                    .placeholder(defHeader)
                    .error(defHeader);
        }
    }


}
