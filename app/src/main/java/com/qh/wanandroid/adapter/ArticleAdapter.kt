package com.qh.wanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.devlibrary.utils.ImageLoader
import com.example.devlibrary.utils.ResourcesUtils
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity

/**
 * @author FQH
 * Create at 2020/4/9.
 * 文章适配器
 */
class ArticleAdapter :
    BaseQuickAdapter<ArticleEntity.DatasBean, BaseViewHolder>(R.layout.item_home_article),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ArticleEntity.DatasBean) {

        item.run {
            if (type == 1) {
                holder.setText(R.id.tvTag, "置顶 ")
                holder.setTextColor(R.id.tvTag, ResourcesUtils.getColor(context,R.color.red))
            } else {
                holder.setText(R.id.tvTag, "")
            }
            holder.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            holder.setText(R.id.tvDate, niceDate)
            val ivArticlePic = holder.getView<ImageView>(R.id.ivArticlePic)
            ivArticlePic.visibility = if (envelopePic.isNullOrBlank()) View.GONE else View.VISIBLE
            holder.getView<View>(R.id.view).visibility = if (envelopePic.isNullOrBlank()) View.GONE else View.VISIBLE
            ImageLoader.load(ivArticlePic, envelopePic)
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