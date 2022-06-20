package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wan.android.R
import com.wan.android.bean.IntegralRecordEntity

/**
 * @author cy
 * Create at 2020/4/14.
 */
class IntegralAdapter :
    BaseQuickAdapter<IntegralRecordEntity.DatasBean, BaseViewHolder>(R.layout.item_integral),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: IntegralRecordEntity.DatasBean) {
        item.apply {
            val desc = desc
            val firstSpace = desc?.indexOf(" ")
            val secondSpace = firstSpace?.plus(1)?.let { desc.indexOf(" ", it) }
            val time = secondSpace?.let { desc.substring(0, it) }
            val title = secondSpace?.plus(1)?.let {
                desc.substring(it)
                    .replace(",", "")
                    .replace("：", "")
                    .replace(" ", "")
            }
            holder.setText(R.id.tvAddIntegralMode, title)
            holder.setText(R.id.tvDate, time)
            holder.setText(R.id.tvAddIntegral, "+$coinCount")
        }
    }

}