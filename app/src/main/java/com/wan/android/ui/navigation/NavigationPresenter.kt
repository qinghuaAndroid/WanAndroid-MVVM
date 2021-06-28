package com.wan.android.ui.navigation

import com.alibaba.android.arouter.launcher.ARouter
import com.wan.common.arouter.ArouterPath
import com.wan.android.bean.ArticleEntity

/**
 * @author cy
 * Create at 2020/5/9.
 */
class NavigationPresenter {

    val clickLabel: (ArticleEntity.DatasBean) -> Unit = { clickLabel(it) }

    private fun clickLabel(datasBean: ArticleEntity.DatasBean) {
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(com.wan.common.constant.Const.WEB_TITLE, datasBean.title)
            .withString(com.wan.common.constant.Const.WEB_URL, datasBean.link)
            .navigation()
    }
}