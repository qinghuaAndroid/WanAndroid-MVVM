package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import com.wan.android.bean.NavigationEntity
import com.wan.android.databinding.ItemNavigationBinding
import com.wan.android.ui.navigation.NavigationPresenter

/**
 * @author cy
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