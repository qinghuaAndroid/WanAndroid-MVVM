package com.qh.wanandroid.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.example.devlibrary.utils.ImageLoader
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.BannerEntity
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
        ImageLoader.load(holder.imageView, data.imagePath)
    }
}