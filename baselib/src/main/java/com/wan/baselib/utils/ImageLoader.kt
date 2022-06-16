package com.wan.baselib.utils

import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.wan.baselib.R
import com.wan.baselib.app.App
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
    fun load(iv: ImageView, url: String?) {
        load(iv, url, defImage)
    }

    @JvmStatic
    fun load(iv: ImageView, res: Int) {
        load(iv, res, defImage)
    }

    @JvmStatic
    fun loadAvatar(iv: ImageView, res: Int) {
        loadAvatar(iv, res, defHeader)
    }

    @JvmStatic
    fun loadAvatar(iv: ImageView, url: String?) {
        Glide.with(iv.context)
            .load(url)
            .apply(ImageLoader.getOptionAvatar())
            .into(iv)
    }

    @JvmStatic
    fun loadAvatar(iv: ImageView, res: Int, defaultPicId: Int) {
        Glide.with(iv.context)
            .load(res)
            .transition(
                DrawableTransitionOptions().crossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                )
            )
            .apply(getOptionAvatar().placeholder(defaultPicId))
            .into(iv)
    }

    @JvmStatic
    fun load(iv: ImageView, url: String?, defaultPicId: Int) {
        Glide.with(iv.context)
            .load(url)
            .apply(RequestOptions().placeholder(defaultPicId).error(defaultPicId))
            .into(iv)
    }

    @JvmStatic
    fun load(iv: ImageView, res: Int, defaultPicId: Int) {
        iv.tag = null //需要清空tag，否则报错
        Glide.with(iv.context)
            .load(res)
            .apply(RequestOptions().placeholder(defaultPicId))
            .into(iv)
    }

    @JvmStatic
    fun load(iv: ImageView, url: String?, requestOptions: RequestOptions) {
        Glide.with(iv.context)
            .load(url)
            .apply(requestOptions)
            .into(iv)
    }

    @JvmStatic
    fun load(iv: ImageView, res: Int, requestOptions: RequestOptions) {
        Glide.with(iv.context)
            .load(res)
            .apply(requestOptions)
            .into(iv)
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
                        App.context(),
                        8f,
                        GlideRoundedCornersTransform.CornerType.ALL
                    )
                )
                .placeholder(defHeader)
                .error(defHeader)
        }
    }
}