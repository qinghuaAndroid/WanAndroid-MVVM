package com.qh.wanandroid.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qh.wanandroid.R
import com.zs.wanandroid.entity.CollectEntity


/**
 * des 收藏适配器
 *
 * @author zs
 * @date 2020-03-13
 */
class CollectAdapter(layoutId: Int) :
    BaseQuickAdapter<CollectEntity.DatasBean, BaseViewHolder>(layoutId), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CollectEntity.DatasBean) {
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