package com.qh.wanandroid.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.donkingliang.labels.LabelsView
import com.example.devlibrary.utils.ImageLoader
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.bean.SystemListEntity

@BindingAdapter(value = ["girlImgUrl"])
fun ImageView.loadGirlImg(url: String) {
    ImageLoader.load(this, url, R.mipmap.ic_girl_default)
}

@BindingAdapter(value = ["imageUrl"])
fun ImageView.loadImage(url: String) {
    ImageLoader.load(this, url)
}

@BindingAdapter(value = ["collectIcon"])
fun ImageView.loadCollectIcon(collect: Boolean) {
    setImageResource(if (collect) R.mipmap.article_collect else R.mipmap.article_un_collect)
}

@BindingAdapter(value = ["systemLabels"])
fun LabelsView.loadSystemLabels(labels: List<SystemListEntity.ChildrenBean>) {
    setLabels(labels) { _, _, data ->
        data.name
    }
}

@BindingAdapter(value = ["onSystemLabelClickListener"])
fun LabelsView.onSystemLabelClickListener(action: (SystemListEntity.ChildrenBean) -> Unit) {
    setOnLabelClickListener { label, data, position ->
        val childrenBean = data as SystemListEntity.ChildrenBean
        action(childrenBean)
    }
}

@BindingAdapter(value = ["navigationLabels"])
fun LabelsView.loadNavigationLabels(labels: List<ArticleEntity.DatasBean>) {
    setLabels(labels) { _, _, data ->
        data.title
    }
}

@BindingAdapter(value = ["onNavigationLabelClickListener"])
fun LabelsView.onNavigationLabelClickListener(action: (ArticleEntity.DatasBean) -> Unit) {
    setOnLabelClickListener { label, data, position ->
        val datasBean = data as ArticleEntity.DatasBean
        action(datasBean)
    }
}

