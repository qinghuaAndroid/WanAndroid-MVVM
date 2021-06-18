package com.wan.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.common.arouter.ArouterPath
import com.wan.baselib.base.BaseFragmentActivity
import com.wan.baselib.base.FragmentModule
import com.wan.login.databinding.ActivityLoginBinding
import java.util.*

/**
 * @author FQH
 * Create at 2020/4/7.
 */
@Route(path = ArouterPath.ACTIVITY_LOGIN)
class LoginActivity : BaseFragmentActivity<ActivityLoginBinding>() {

    override fun getContainerViewId(): Int = R.id.fragmentContainerView

    override fun getDefaultModule(): FragmentModule =
        FragmentModule(LoginFragment(this), getString(R.string.login))

    override fun getFragmentModule(): HashMap<String, FragmentModule> {
        modules = HashMap<String, FragmentModule>()
        modules[Const.FRAGMENT_REGISTER] =
            FragmentModule(RegisterFragment(this@LoginActivity), getString(R.string.register))
        return modules
    }

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun initView() {

    }

    override fun loadData() {

    }

}