package com.qh.wanandroid.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
    @JvmField
    var imageView: ImageView

    init {
        imageView = view as ImageView
    }
}