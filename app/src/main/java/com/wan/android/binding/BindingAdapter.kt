package com.wan.android.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.donkingliang.labels.LabelsView
import com.wan.android.R
import com.wan.android.bean.ArticleEntity
import com.wan.android.bean.SystemListEntity

@BindingAdapter(value = ["imageUrl"])
fun ImageView.loadImage(url: String) {
    //使用glide加载
    this.load(url) {
        placeholder(com.wan.baselib.R.mipmap.ic_default_img)
    }
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
fun LabelsView.loadNavigationLabels(labels: List<ArticleEntity.DatasBean>?) {
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

