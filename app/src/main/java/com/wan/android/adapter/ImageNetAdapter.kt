package com.wan.android.adapter

import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.wan.android.R
import com.wan.android.bean.BannerEntity
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * 自定义布局，网络图片
 */
class ImageNetAdapter(mDatas: List<BannerEntity>?) :
    BannerAdapter<BannerEntity, ImageHolder>(mDatas) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView =
            BannerUtils.getView(parent, R.layout.banner_image) as ImageView
        return ImageHolder(imageView)
    }

    override fun onBindView(
        holder: ImageHolder,
        data: BannerEntity,
        position: Int,
        size: Int
    ) {
        holder.imageView.load(data.imagePath)
    }
}