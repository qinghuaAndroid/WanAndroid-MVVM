package com.qh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.SearchHistoryBean

class SearchHistoryAdapter()
    : BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(R.layout.item_search_history),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: SearchHistoryBean) {
        holder.setText(R.id.tv_search_key, item.key)
    }
}