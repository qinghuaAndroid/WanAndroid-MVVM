package com.qh.wanandroid.ui.setting

import android.content.Intent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.common.constant.Const
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvp.BaseMvpActivity
import com.example.devlibrary.utils.CacheUtils
import com.example.devlibrary.utils.SettingUtil
import com.example.devlibrary.utils.StringUtils
import com.example.devlibrary.utils.versionName
import com.qh.wanandroid.R
import com.qh.wanandroid.databinding.ActivitySettingBinding
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class SettingActivity :
    BaseMvpActivity<SettingContract.View, SettingContract.Presenter, ActivitySettingBinding>(),
    SettingContract.View {

    private val mmkv by lazy { MMKV.defaultMMKV() }
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
        tvVersionValue.text = versionName
        tvLogout.visibility = if (isLogin) View.VISIBLE else View.GONE
        tvLogout.onClick { mPresenter?.logout() }
        tvProject.onClick { github() }
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
                LiveEventBusHelper.post(Const.THEME_COLOR, color)
            }
            positiveButton(R.string.done)
            negativeButton(android.R.string.cancel)
            lifecycleOwner(this@SettingActivity)
        }
    }

    private fun github() {
        Intent(this, BrowserNormalActivity::class.java).run {
            putExtra(Const.WEB_TITLE, "WanAndroid-qh")
            putExtra(Const.WEB_URL, "https://github.com/qinghuaAndroid/WanAndroid-qh")
            startActivity(this)
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
        LiveEventBusHelper.post(Const.LOGOUT_SUCCESS, true)
        finish()
    }
}