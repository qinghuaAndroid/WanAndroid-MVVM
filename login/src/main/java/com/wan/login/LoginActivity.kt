package com.wan.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.baselib.base.BaseActivity
import com.wan.common.arouter.ArouterPath
import com.wan.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author cy
 * Create at 2020/4/7.
 * @see <a href="https://mp.weixin.qq.com/s/kqjYN4LqzONkDAHI3rLRTQ">navigation</a>
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {

    }

    override fun loadData() {

    }

    override fun initData() {

    }

}