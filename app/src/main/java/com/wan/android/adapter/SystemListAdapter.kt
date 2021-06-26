package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import com.wan.android.bean.SystemListEntity
import com.wan.android.databinding.ItemSystemBinding
import com.wan.android.ui.system.SystemListPresenter

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListAdapter :
    BaseQuickAdapter<SystemListEntity,
            BaseDataBindingHolder<ItemSystemBinding>>(R.layout.item_system), LoadMoreModule {

    private val presenter: SystemListPresenter by lazy { SystemListPresenter() }

    override fun convert(holder: BaseDataBindingHolder<ItemSystemBinding>, item: SystemListEntity) {
        holder.dataBinding?.let {
            it.systemListEntity = item
            it.presenter = presenter
            it.executePendingBindings()
        }
    }
}