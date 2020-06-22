package com.qh.wanandroid.ui.system

import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.arouter.ArouterPath
import com.qh.wanandroid.bean.SystemListEntity
import com.qh.wanandroid.const.Const

/**
 * @author FQH
 * Create at 2020/5/9.
 */
class SystemListPresenter {

    val clickLabel: (SystemListEntity.ChildrenBean) -> Unit = { clickLabel(it) }

    private fun clickLabel(childrenBean: SystemListEntity.ChildrenBean) {
        val id = childrenBean.id
        val title = childrenBean.name
        ARouter.getInstance().build(ArouterPath.ACTIVITY_SYSTEM)
            .withInt(Const.SYSTEM_ID, id)
            .withString(Const.SYSTEM_TITLE, title)
            .navigation()
    }
}