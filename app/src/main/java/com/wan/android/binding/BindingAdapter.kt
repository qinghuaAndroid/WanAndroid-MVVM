package com.wan.android.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.donkingliang.labels.LabelsView
import com.wan.android.R
import o.BY
import o.Q

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
fun LabelsView.loadSystemLabels(labels: List<Q.ChildrenBean>) {
    setLabels(labels) { _, _, data ->
        data.name
    }
}

@BindingAdapter(value = ["onSystemLabelClickListener"])
fun LabelsView.onSystemLabelClickListener(action: (Q.ChildrenBean) -> Unit) {
    setOnLabelClickListener { label, data, position ->
        val childrenBean = data as Q.ChildrenBean
        action(childrenBean)
    }
}

@BindingAdapter(value = ["navigationLabels"])
fun LabelsView.loadNavigationLabels(labels: List<BY.DatasBean>?) {
    setLabels(labels) { _, _, data ->
        data.title
    }
}

@BindingAdapter(value = ["onNavigationLabelClickListener"])
fun LabelsView.onNavigationLabelClickListener(action: (BY.DatasBean) -> Unit) {
    setOnLabelClickListener { label, data, position ->
        val datasBean = data as BY.DatasBean
        action(datasBean)
    }
}

