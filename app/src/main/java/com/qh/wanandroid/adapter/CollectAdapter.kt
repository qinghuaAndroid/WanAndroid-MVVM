package com.qh.wanandroid.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity

/**
 * @author FQH
 * Create at 2020/4/9.
 * 收藏适配器
 */
class CollectAdapter(layoutId: Int) :
    BaseQuickAdapter<ArticleEntity.DatasBean, BaseViewHolder>(layoutId), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ArticleEntity.DatasBean) {
        item.run {
            holder.setText(R.id.tvTag, "")
            holder.setText(R.id.tvAuthor, author)
            holder.setText(R.id.tvDate, niceDate)
            holder.setText(R.id.tvTitle, title)
            holder.setText(R.id.tvChapterName, chapterName)
            holder.getView<ImageView>(R.id.ivCollect)
                .apply {
                    setImageResource(R.mipmap.article_collect)
                }
        }
    }
}