package com.wan.android.ui.setting

import android.annotation.SuppressLint
import android.view.View
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wan.android.R
import com.wan.android.databinding.ActivitySettingBinding
import com.wan.android.ui.account.AccountViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.StringUtils
import com.wan.baselib.utils.versionName
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.sdk27.coroutines.onClick


/**
 * @author cy
 * Create at 2020/4/10.
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_SETTING)
class SettingActivity : BaseVMActivity<SettingViewModel, ActivitySettingBinding>() {

    private val settingViewModel by viewModels<SettingViewModel>()
    private val accountViewModel by viewModels<AccountViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initData() {

    }

    override fun initView() {
        setTitle(R.string.setting)
        binding.tvClearValue.onClick { settingViewModel.clearCache() }
        binding.tvVersionValue.text = StringUtils.format("v %s", versionName)
        binding.tvColor.onClick { setThemeColor() }
        binding.tvLogout.visibility = if (accountViewModel.isLogin) View.VISIBLE else View.GONE
        binding.tvLogout.onClick { accountViewModel.logout() }
    }

    @SuppressLint("CheckResult")
    private fun setThemeColor() {
        MaterialDialog(this).show {
            title(R.string.choose_theme_color)
            colorChooser(
                ColorPalette.Primary,
                ColorPalette.PrimarySub
            ) { _, color ->
                SettingUtil.setColor(color)
                initColor()
                LiveEventBus.get<Int>(Const.THEME_COLOR).post(color)
            }
            positiveButton(R.string.done)
            negativeButton(android.R.string.cancel)
            lifecycleOwner(this@SettingActivity)
        }
    }

    override fun loadData() {
        settingViewModel.getCacheSize()
    }

    override fun startObserve() {
        settingViewModel.cacheValue.observe(this) { cacheValue ->
            binding.tvClearValue.text = cacheValue
        }
        accountViewModel.uiState.observe(this) {
            if (it.showLoading) showProgressDialog() else dismissProgressDialog()
            it.showSuccess?.let { success ->
                if (success) {
                    LiveEventBus.get<Boolean>(Const.LOGOUT_SUCCESS).post(true)
                    finish()
                }
            }
            it.showError?.let { errorMsg -> showToast(errorMsg) }
        }
    }
}