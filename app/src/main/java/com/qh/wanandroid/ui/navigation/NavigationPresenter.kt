package com.qh.wanandroid.ui.navigation

import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.arouter.ArouterPath
import com.qh.wanandroid.bean.ArticleEntity

/**
 * @author FQH
 * Create at 2020/5/9.
 */
class NavigationPresenter {

    val clickLabel: (ArticleEntity.DatasBean) -> Unit = { clickLabel(it) }

    private fun clickLabel(datasBean: ArticleEntity.DatasBean) {
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(com.example.common.constant.Const.WEB_TITLE, datasBean.title)
            .withString(com.example.common.constant.Const.WEB_URL, datasBean.link)
            .navigation()
    }
}