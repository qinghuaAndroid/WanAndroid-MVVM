package com.wan.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.arouter.ArouterPath
import com.example.devlibrary.base.BaseFragmentActivity
import com.example.devlibrary.base.FragmentModule
import com.wan.login.databinding.ActivityLoginBinding
import java.util.*

/**
 * @author FQH
 * Create at 2020/4/7.
 */
@Route(path = ArouterPath.ACTIVITY_LOGIN)
class LoginActivity : BaseFragmentActivity<ActivityLoginBinding>() {

    private var modules: HashMap<String, FragmentModule>? = null

    override fun getContainerViewId(): Int = R.id.fragmentContainerView

    override fun getDefaultModule(): FragmentModule =
        FragmentModule(LoginFragment(this), getString(R.string.login))

    override fun getFragmentModule(): HashMap<String, FragmentModule> {
        return modules ?: HashMap<String, FragmentModule>().apply {
            this[Const.FRAGMENT_REGISTER] =
                FragmentModule(RegisterFragment(this@LoginActivity), getString(R.string.register))
        }
    }

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun initView() {

    }

    override fun loadData() {

    }

}