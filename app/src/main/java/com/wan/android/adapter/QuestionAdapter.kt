package com.wan.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.android.R
import com.wan.android.bean.ArticleEntity
import com.wan.android.databinding.ItemHomeArticleBinding

class QuestionAdapter :
    PagingDataAdapter<ArticleEntity.DatasBean, BaseDataBindingHolder<ItemHomeArticleBinding>>(
        COMPARATOR
    ) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleEntity.DatasBean>() {
            override fun areItemsTheSame(
                oldItem: ArticleEntity.DatasBean,
                newItem: ArticleEntity.DatasBean
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ArticleEntity.DatasBean,
                newItem: ArticleEntity.DatasBean
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseDataBindingHolder<ItemHomeArticleBinding>,
        position: Int
    ) {
        holder.dataBinding?.let {
            it.article = getItem(position)
            it.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDataBindingHolder<ItemHomeArticleBinding> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_article, parent, false)
        return BaseDataBindingHolder(view)
    }

}