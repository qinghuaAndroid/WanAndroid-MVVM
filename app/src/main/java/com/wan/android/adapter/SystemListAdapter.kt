package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import o.Q
import com.wan.android.databinding.ItemSystemBinding
import q.S

/**
 * @author cy
 * Create at 2020/4/8.
 */
class SystemListAdapter :
    BaseQuickAdapter<Q,
            BaseDataBindingHolder<ItemSystemBinding>>(R.layout.item_system), LoadMoreModule {

    private val presenter: S by lazy { S() }

    override fun convert(holder: BaseDataBindingHolder<ItemSystemBinding>, item: Q) {
        holder.dataBinding?.let {
            it.systemListEntity = item
            it.presenter = presenter
            it.executePendingBindings()
        }
    }
}