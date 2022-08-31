package com.wan.android.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.android.R
import com.wan.android.databinding.ActivitySettingBinding
import com.wan.baselib.flowbus.SharedFlowBus
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.baselib.utils.SettingUtil
import com.wan.baselib.utils.StringUtils
import com.wan.baselib.utils.versionName
import com.wan.common.arouter.ArouterPath
import com.wan.common.constant.Const
import dagger.hilt.android.AndroidEntryPoint
import splitties.views.onClick


/**
 * @author cy
 * Create at 2020/4/10.
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_SETTING)
class SettingActivity : BaseVMActivity<SettingViewModel, ActivitySettingBinding>() {

    private val settingViewModel by viewModels<SettingViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        setTitle(R.string.setting)
        binding.tvClearValue.onClick { settingViewModel.clearCache() }
        binding.tvVersionValue.text = StringUtils.format("v %s", versionName)
        binding.tvColor.onClick { setThemeColor() }
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
                SharedFlowBus.with<Int>(Const.THEME_COLOR).tryEmit(color)
            }
            positiveButton(R.string.done)
            negativeButton(android.R.string.cancel)
            lifecycleOwner(this@SettingActivity)
        }
    }

    override fun loadData() {
        settingViewModel.getCacheSize()
    }

    override fun subscribeUi() {
        settingViewModel.cacheValue.observe(this) { cacheValue ->
            binding.tvClearValue.text = cacheValue
        }
    }
}