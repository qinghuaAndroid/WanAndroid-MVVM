package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import o.O
import com.wan.android.databinding.ItemNavigationBinding
import p.P

/**
 * @author cy
 * Create at 2020/4/8.
 */
class NavigationAdapter :
    BaseQuickAdapter<O,
            BaseDataBindingHolder<ItemNavigationBinding>>(R.layout.item_navigation), LoadMoreModule {

    private val presenter: P by lazy { P() }

    override fun convert(holder: BaseDataBindingHolder<ItemNavigationBinding>, item: O) {
        holder.dataBinding?.let {
            it.navigationEntity = item
            it.presenter = presenter
            it.executePendingBindings()
        }
    }
}