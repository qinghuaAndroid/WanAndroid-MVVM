package com.qh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.NavigationEntity
import com.qh.wanandroid.databinding.ItemNavigationBinding
import com.qh.wanandroid.ui.navigation.NavigationPresenter

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class NavigationAdapter :
    BaseQuickAdapter<NavigationEntity,
            BaseDataBindingHolder<ItemNavigationBinding>>(R.layout.item_navigation), LoadMoreModule {

    private val presenter: NavigationPresenter by lazy { NavigationPresenter() }

    override fun convert(holder: BaseDataBindingHolder<ItemNavigationBinding>, item: NavigationEntity) {
        holder.dataBinding?.let {
            it.navigationEntity = item
            it.presenter = presenter
            it.executePendingBindings()
        }
    }
}