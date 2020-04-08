package com.qh.wanandroid.listener

import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author FQH
 * Create at 2020/4/8.
 */
interface OnLabelClickListener {
    /**
     * @param i 外层角标
     * @param j 内层角标
     */
    fun onLabelClick(helper: BaseViewHolder, i: Int, j: Int)
}