package com.qh.wanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.devlibrary.utils.ResourcesUtils
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity

/**
 * 文章适配器
 * @author zs
 * @date 2020-03-16修改
 */
class ArticleAdapter :
    BaseQuickAdapter<ArticleEntity.DatasBean, BaseViewHolder>(R.layout.item_home_article),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ArticleEntity.DatasBean) {

        item.run {
            if (type == 1) {
                holder.setText(R.id.tvTag, "置顶 ")
                holder.setTextColor(R.id.tvTag, ResourcesUtils.getColor(R.color.red))
            } else {
                holder.setText(R.id.tvTag, "")
            }
            holder.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            holder.setText(R.id.tvDate, niceDate)
            holder.setText(R.id.tvTitle, Html.fromHtml(title))
            holder.setText(R.id.tvChapterName, superChapterName)
            holder.getView<ImageView>(R.id.ivCollect)
                .apply {
                    if (item.collect) {
                        setImageResource(R.mipmap.article_collect)
                    } else {
                        setImageResource(R.mipmap.article_un_collect)
                    }
                }
        }
    }
}