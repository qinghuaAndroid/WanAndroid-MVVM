package com.wan.android.ui.setting

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.wan.android.R
import com.wan.android.databinding.ActivitySettingBinding
import com.wan.baselib.mvp.BaseMvpActivity
import com.wan.baselib.utils.CacheUtils
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.StringUtils
import com.wan.baselib.utils.versionName
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author cy
 * Create at 2020/4/10.
 */
@Route(path = ArouterPath.ACTIVITY_SETTING)
class SettingActivity :
    BaseMvpActivity<SettingContract.View, SettingContract.Presenter, ActivitySettingBinding>(),
    SettingContract.View {

    private val mmkv by lazy { MMKV.defaultMMKV()!! }
    private val isLogin by lazy { mmkv.decodeBool(Const.IS_LOGIN, false) }

    override fun createPresenter(): SettingContract.Presenter = SettingPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_setting

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setTitle(R.string.setting)
        tvClearValue.text = CacheUtils.getTotalCacheSize(this)
        tvClearValue.onClick { clearCache() }
        tvVersionValue.text = StringUtils.format("v %s", versionName)
        tvLogout.visibility = if (isLogin) View.VISIBLE else View.GONE
        tvLogout.onClick { mPresenter?.logout() }
        tvColor.onClick { setThemeColor() }
    }

    private fun setThemeColor() {
        MaterialDialog(this).show {
            title(R.string.choose_theme_color)
            colorChooser(
                ColorPalette.Primary,
                ColorPalette.PrimarySub
            ) { _, color ->
                SettingUtil.setColor(color)
                initColor()
                LiveEventBus.get(Const.THEME_COLOR).post(color)
            }
            positiveButton(R.string.done)
            negativeButton(android.R.string.cancel)
            lifecycleOwner(this@SettingActivity)
        }
    }

    override fun loadData() {

    }

    private fun clearCache() {
        CacheUtils.clearAllCache(this)
        tvClearValue.text = StringUtils.format("%s", "0M")
    }

    override fun logoutSuccess() {
        mmkv.encode(Const.IS_LOGIN, false)
        mmkv.removeValueForKey(Const.USER_GSON)
        LiveEventBus.get(Const.LOGOUT_SUCCESS).post(true)
        finish()
    }
}