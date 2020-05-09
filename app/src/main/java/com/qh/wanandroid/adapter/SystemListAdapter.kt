package com.qh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.SystemListEntity
import com.qh.wanandroid.databinding.ItemSystemBinding
import com.qh.wanandroid.ui.system.SystemListPresenter

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