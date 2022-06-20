package com.wan.baselib.utils

import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.wan.baselib.R
import com.wan.baselib.ext.appContext
import com.wan.baselib.transform.GlideCircleBorderTransform
import com.wan.baselib.transform.GlideRoundedCornersTransform

/**
 * Created by cy on 13/10/2021.
 */
object ImageLoader {

    private const val STYLE_CIRCLE = "circle"
    private const val STYLE_ROUND = "round"

    private var mOptionAvatar: RequestOptions? = null

    private val defImage = R.mipmap.ic_default_img
    private val defHeader = R.mipmap.ic_default_avatar
    private var mStyle = STYLE_ROUND

    @JvmStatic
    fun load(imageView: ImageView, url: String?) {
        load(imageView, url, defImage)
    }

    @JvmStatic
    fun load(imageView: ImageView, res: Int) {
        load(imageView, res, defImage)
    }

    @JvmStatic
    fun loadAvatar(imageView: ImageView, res: Int) {
        loadAvatar(imageView, res, defHeader)
    }

    @JvmStatic
    fun loadAvatar(imageView: ImageView, url: String?) {
        Glide.with(imageView)
            .load(url)
            .apply(getOptionAvatar())
            .into(imageView)
    }

    @JvmStatic
    fun loadAvatar(imageView: ImageView, res: Int, defaultPicId: Int) {
        Glide.with(imageView)
            .load(res)
            .transition(
                DrawableTransitionOptions().crossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                )
            )
            .apply(getOptionAvatar().placeholder(defaultPicId))
            .into(imageView)
    }

    @JvmStatic
    fun load(imageView: ImageView, url: String?, defaultPicId: Int) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions().placeholder(defaultPicId).error(defaultPicId))
            .into(imageView)
    }

    @JvmStatic
    fun load(imageView: ImageView, res: Int, defaultPicId: Int) {
        imageView.tag = null //需要清空tag，否则报错
        Glide.with(imageView)
            .load(res)
            .apply(RequestOptions().placeholder(defaultPicId))
            .into(imageView)
    }

    @JvmStatic
    fun load(imageView: ImageView, url: String?, requestOptions: RequestOptions) {
        Glide.with(imageView)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    @JvmStatic
    fun load(imageView: ImageView, res: Int, requestOptions: RequestOptions) {
        Glide.with(imageView)
            .load(res)
            .apply(requestOptions)
            .into(imageView)
    }

    @Synchronized
    fun getOptionAvatar(): RequestOptions {
        if (mOptionAvatar == null) {
            initHeaderOption()
        }
        return mOptionAvatar!!
    }

    @Synchronized
    fun getOptionAvatar(style: String): RequestOptions {
        mStyle = style
        if (mOptionAvatar == null) {
            initHeaderOption()
        }
        return mOptionAvatar!!
    }

    private fun initHeaderOption() {
        if (STYLE_CIRCLE == mStyle) {
            mOptionAvatar = RequestOptions
                .bitmapTransform(GlideCircleBorderTransform(10f, Color.WHITE))
                .placeholder(defHeader)
                .error(defHeader)
        } else {
            mOptionAvatar = RequestOptions()
                .optionalTransform(
                    GlideRoundedCornersTransform(
                        appContext,
                        8f,
                        GlideRoundedCornersTransform.CornerType.ALL
                    )
                )
                .placeholder(defHeader)
                .error(defHeader)
        }
    }
}