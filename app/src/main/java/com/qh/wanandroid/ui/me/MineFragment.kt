package com.qh.wanandroid.ui.me

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.example.common.constant.Const
import com.example.devlibrary.helper.LiveEventBusHelper
import com.example.devlibrary.mvvm.BaseVMFragment
import com.example.devlibrary.utils.StringUtils
import com.qh.wanandroid.R
import com.qh.wanandroid.databinding.FragmentMineBinding
import com.qh.wanandroid.ext.startActivity
import com.qh.wanandroid.ui.BrowserNormalActivity
import com.qh.wanandroid.ui.collect.CollectActivity
import com.qh.wanandroid.ui.girl.GirlActivity
import com.qh.wanandroid.ui.integral.IntegralActivity
import com.qh.wanandroid.ui.login.LoginActivity
import com.qh.wanandroid.ui.setting.SettingActivity
import com.qh.wanandroid.ui.share.ShareListActivity
import com.tencent.mmkv.MMKV
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author FQH
 * Create at 2020/4/2.
 */
class MineFragment : BaseVMFragment<MineViewModel, FragmentMineBinding>() {

    private val isLogin by lazy { MMKV.defaultMMKV().decodeBool(Const.IS_LOGIN, false) }
    private val mViewModel: MineViewModel by viewModel()

    override fun startObserve() {
        mViewModel.integralData.observe(this,
            Observer {
                mBinding.tvUserName.text = it.username
                mBinding.tvId.text = StringUtils.format("id:%s", it.userId.toString())
                mBinding.tvRanking.text = it.rank.toString()
                mBinding.tvIntegral.text = it.coinCount.toString()
            })
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_mine

    override fun initData() {
        LiveEventBusHelper.observe(Const.LOGIN_SUCCESS, Boolean::class.java, this,
            Observer<Boolean> { loadData() })
        LiveEventBusHelper.observe(Const.LOGOUT_SUCCESS, Boolean::class.java, this,
            Observer<Boolean> { logout() })
    }

    private fun logout() {
        mBinding.tvUserName.text = "请先登录"
        mBinding.tvId.text = StringUtils.format("id:%s", "---")
        mBinding.tvRanking.text = "0"
        mBinding.tvIntegral.text = "0"
    }

    override fun initView(view: View) {
        mBinding.tvUserName.onClick { if (isLogin.not()) startActivity<LoginActivity>() }
        mBinding.rlIntegral.onClick { startActivity(IntegralActivity::class.java, true) }//我的积分
        mBinding.rlCollect.onClick { startActivity(CollectActivity::class.java, true) }//我的收藏
        mBinding.rlArticle.onClick { startActivity(ShareListActivity::class.java, true) }//我的文章
        mBinding.rlWebsite.onClick { toWanAndroid() }//网站
        mBinding.rlGirl.onClick { startActivity<GirlActivity>() }//轻松一下
        mBinding.rlSet.onClick { startActivity<SettingActivity>() }//设置
    }

    private fun toWanAndroid() {
        Intent(activity, BrowserNormalActivity::class.java).run {
            putExtra(Const.WEB_TITLE, "WAN ANDROID")
            putExtra(Const.WEB_URL, "https://www.wanandroid.com/")
            startActivity(this)
        }
    }

    override fun loadData() {
        mViewModel.getIntegral()
    }

}