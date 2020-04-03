package com.qh.wanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.devlibrary.utils.ResourcesUtils
import com.qh.wanandroid.R
import com.qh.wanandroid.bean.ArticleEntity

/**
 * 文章适配器
 * @author zs
 * @date 2020-03-16修改
 */
class ArticleAdapter(list: MutableList<ArticleEntity.DatasBean>?) :
    BaseQuickAdapter<ArticleEntity.DatasBean,
            BaseViewHolder>(R.layout.item_home_article, list) {

    override fun convert(helper: BaseViewHolder, item: ArticleEntity.DatasBean?) {

        item?.run {
            if (type == 1) {
                helper.setText(R.id.tvTag, "置顶 ")
                helper.setTextColor(R.id.tvTag, ResourcesUtils.getColor(R.color.red))
            } else {
                helper.setText(R.id.tvTag, "")
            }
            helper.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            helper.setText(R.id.tvDate, niceDate)
            helper.setText(R.id.tvTitle, Html.fromHtml(title))
            helper.setText(R.id.tvChapterName, superChapterName)
            helper.getView<ImageView>(R.id.ivCollect)
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