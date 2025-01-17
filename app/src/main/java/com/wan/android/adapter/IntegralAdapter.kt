package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import o.CB
import com.wan.android.databinding.ItemIntegralBinding

/**
 * @author cy
 * Create at 2020/4/14.
 */
class IntegralAdapter :
    BaseQuickAdapter<CB.DatasBean, BaseDataBindingHolder<ItemIntegralBinding>>(R.layout.item_integral),
    LoadMoreModule {

    override fun convert(
        holder: BaseDataBindingHolder<ItemIntegralBinding>,
        item: CB.DatasBean
    ) {
        holder.dataBinding?.let {
            it.data = item
            it.executePendingBindings()
        }
    }

}