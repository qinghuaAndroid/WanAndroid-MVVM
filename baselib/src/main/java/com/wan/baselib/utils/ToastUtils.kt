package com.wan.baselib.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import com.wan.baselib.ext.appContext

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/9/29
 * desc  : 吐司相关工具类
</pre> *
 */
object ToastUtils {

    private var sToast: Toast? = null
    private val sHandler = Handler(Looper.getMainLooper())
    private var isJumpWhenMore = false

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *
     * `true`: 弹出新吐司<br></br>`false`: 只修改文本内容
     *
     * 如果为`false`的话可用来做显示任意时长的吐司
     */
    fun init(isJumpWhenMore: Boolean) {
        this.isJumpWhenMore = isJumpWhenMore
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    fun showShortToastSafe(text: String) {
        sHandler.post { showToast(text, Toast.LENGTH_SHORT) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    fun showShortToastSafe(@StringRes resId: Int) {
        sHandler.post { showToast(resId, Toast.LENGTH_SHORT) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showShortToastSafe(@StringRes resId: Int, vararg args: Any?) {
        sHandler.post { showToast(resId, Toast.LENGTH_SHORT, *args) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showShortToastSafe(format: String, vararg args: Any?) {
        sHandler.post { showToast(format, Toast.LENGTH_SHORT, *args) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    fun showLongToastSafe(text: String) {
        sHandler.post { showToast(text, Toast.LENGTH_LONG) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    fun showLongToastSafe(@StringRes resId: Int) {
        sHandler.post { showToast(resId, Toast.LENGTH_LONG) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showLongToastSafe(@StringRes resId: Int, vararg args: Any?) {
        sHandler.post { showToast(resId, Toast.LENGTH_LONG, *args) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showLongToastSafe(format: String, vararg args: Any?) {
        sHandler.post { showToast(format, Toast.LENGTH_LONG, *args) }
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    fun showShortToast(text: String) {
        showToast(text, Toast.LENGTH_SHORT)
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    fun showShortToast(@StringRes resId: Int) {
        showToast(resId, Toast.LENGTH_SHORT)
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showShortToast(@StringRes resId: Int, vararg args: Any?) {
        showToast(resId, Toast.LENGTH_SHORT, *args)
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showShortToast(format: String, vararg args: Any?) {
        showToast(format, Toast.LENGTH_SHORT, *args)
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    fun showLongToast(text: String) {
        showToast(text, Toast.LENGTH_LONG)
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    fun showLongToast(@StringRes resId: Int) {
        showToast(resId, Toast.LENGTH_LONG)
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showLongToast(@StringRes resId: Int, vararg args: Any?) {
        showToast(resId, Toast.LENGTH_LONG, *args)
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showLongToast(format: String, vararg args: Any?) {
        showToast(format, Toast.LENGTH_LONG, *args)
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private fun showToast(@StringRes resId: Int, duration: Int) {
        showToast(appContext.resources.getString(resId), duration)
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private fun showToast(@StringRes resId: Int, duration: Int, vararg args: Any?) {
        showToast(String.format(appContext.resources.getString(resId), *args), duration)
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private fun showToast(format: String, duration: Int, vararg args: Any?) {
        showToast(String.format(format, *args), duration)
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private fun showToast(text: String, duration: Int) {
        if (isJumpWhenMore) {
            cancelToast()
        }
        if (sToast == null) {
            sToast = Toast.makeText(appContext, text, duration)
        } else {
            sToast!!.setText(text)
            sToast!!.duration = duration
        }
        sToast!!.show()
    }

    /**
     * 取消吐司显示
     */
    private fun cancelToast() {
        if (sToast != null) {
            sToast!!.cancel()
            sToast = null
        }
    }
}