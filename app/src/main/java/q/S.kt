package q

import com.alibaba.android.arouter.launcher.ARouter
import com.wan.common.arouter.ArouterPath
import o.Q
import com.wan.android.constant.Const

/**
 * @author cy
 * Create at 2020/5/9.
 */
class S {

    val clickLabel: (Q.ChildrenBean) -> Unit = { clickLabel(it) }

    private fun clickLabel(childrenBean: Q.ChildrenBean) {
        val id = childrenBean.id
        val title = childrenBean.name
        ARouter.getInstance().build(ArouterPath.ACTIVITY_SYSTEM)
            .withInt(Const.SYSTEM_ID, id)
            .withString(Const.SYSTEM_TITLE, title)
            .navigation()
    }
}