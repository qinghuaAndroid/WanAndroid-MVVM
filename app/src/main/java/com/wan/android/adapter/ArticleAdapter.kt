package com.wan.android.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import com.wan.android.bean.ArticleEntity
import com.wan.android.databinding.ItemHomeArticleBinding

/**
 * @author FQH
 * Create at 2020/4/9.
 * 文章适配器
 */
class ArticleAdapter :
    BaseQuickAdapter<ArticleEntity.DatasBean, BaseDataBindingHolder<ItemHomeArticleBinding>>(R.layout.item_home_article),
    LoadMoreModule {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeArticleBinding>,
        item: ArticleEntity.DatasBean
    ) {
        holder.dataBinding?.let {
            it.article = item
            it.executePendingBindings()
        }
    }
}