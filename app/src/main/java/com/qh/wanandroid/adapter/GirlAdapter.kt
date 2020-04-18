package com.qh.wanandroid.adapter

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.GankIoDataBean
import com.qh.wanandroid.databinding.ItemGirlBinding


/**
 * @author FQH
 * Create at 2020/4/9.
 */
class GirlAdapter :
    BaseQuickAdapter<GankIoDataBean.ResultBean, BaseDataBindingHolder<ItemGirlBinding>>(R.layout.item_girl),
    LoadMoreModule {
    override fun convert(
        holder: BaseDataBindingHolder<ItemGirlBinding>,
        item: GankIoDataBean.ResultBean
    ) {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/helvetica.ttf")
        holder.getView<TextView>(R.id.tv_desc).typeface = typeface
        val binding = holder.dataBinding
        binding?.let {
            it.bean = item
            it.executePendingBindings()
        }
    }
}