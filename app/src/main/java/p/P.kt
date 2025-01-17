package p

import com.alibaba.android.arouter.launcher.ARouter
import com.wan.common.arouter.ArouterPath
import o.BY

/**
 * @author cy
 * Create at 2020/5/9.
 */
class P {

    val clickLabel: (BY.DatasBean) -> Unit = { clickLabel(it) }

    private fun clickLabel(datasBean: BY.DatasBean) {
        ARouter.getInstance().build(ArouterPath.ACTIVITY_BROWSER)
            .withString(com.wan.common.constant.Const.WEB_TITLE, datasBean.title)
            .withString(com.wan.common.constant.Const.WEB_URL, datasBean.link)
            .navigation()
    }
}