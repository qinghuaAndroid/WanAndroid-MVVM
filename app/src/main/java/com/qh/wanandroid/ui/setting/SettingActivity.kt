package com.qh.wanandroid.ui.setting

import com.example.common.constant.Const
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvp.BaseMvpActivity
import com.example.devlibrary.utils.CacheUtils
import com.example.devlibrary.utils.Preference
import com.example.devlibrary.utils.StringUtils
import com.qh.wanandroid.R
import com.qh.wanandroid.databinding.ActivitySettingBinding
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/4/10.
 */
class SettingActivity :
    BaseMvpActivity<SettingContract.View, SettingContract.Presenter, ActivitySettingBinding>(),
    SettingContract.View {

    private var isLogin by Preference(Const.IS_LOGIN, false)
    private var userJson by Preference(Const.USER_GSON, "")

    override fun createPresenter(): SettingContract.Presenter = SettingPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_setting

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        tvClearValue.text = CacheUtils.getTotalCacheSize(this)
        tvClearValue.onClick { clearCache() }
        tvLogout.onClick { mPresenter?.logout() }
    }

    override fun loadData() {

    }

    private fun clearCache() {
        CacheUtils.clearAllCache(this)
        tvClearValue.text = StringUtils.format("%s", "0M")
    }

    override fun logoutSuccess() {
        isLogin = false
        userJson = ""
        LiveEventBusHelper.post(Const.LOGOUT_SUCCESS, true)
        finish()
    }
}