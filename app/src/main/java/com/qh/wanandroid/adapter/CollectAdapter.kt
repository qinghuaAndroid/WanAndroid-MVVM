package com.qh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.databinding.ItemHomeArticleBinding

/**
 * @author FQH
 * Create at 2020/4/9.
 * 收藏适配器
 */
class CollectAdapter :
    BaseQuickAdapter<ArticleEntity.DatasBean, BaseDataBindingHolder<ItemHomeArticleBinding>>(R.layout.item_home_article),
    LoadMoreModule {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeArticleBinding>,
        item: ArticleEntity.DatasBean
    ) {
        val binding = holder.dataBinding
        binding?.let {
            it.article = item
            it.collected = true
            it.executePendingBindings()
        }
    }
}