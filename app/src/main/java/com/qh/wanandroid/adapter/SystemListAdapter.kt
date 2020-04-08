package com.qh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.SystemListEntity
import com.qh.wanandroid.listener.OnLabelClickListener

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListAdapter(list: MutableList<SystemListEntity>?) :
    BaseQuickAdapter<SystemListEntity,
            BaseViewHolder>(R.layout.item_system, list), LoadMoreModule {

    private var mOnLabelClickListener: OnLabelClickListener? = null

    fun setOnLabelClickListener(listener: OnLabelClickListener?) {
        this.mOnLabelClickListener = listener
    }

    override fun convert(holder: BaseViewHolder, item: SystemListEntity) {
        item.let {
            holder.setText(R.id.tvTitle, item.name)
            holder.getView<LabelsView>(R.id.labelsView).apply {
                setLabels(item.children) { _, _, data ->
                    data.name
                }
                //标签的点击监听
                setOnLabelClickListener { _, _, position ->
                    mOnLabelClickListener?.onLabelClick(
                        holder,
                        holder.adapterPosition,
                        position
                    )
                }
            }
        }
    }
}