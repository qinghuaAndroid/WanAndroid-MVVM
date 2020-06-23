package com.example.devlibrary.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.devlibrary.constant.Const
import java.util.*

/**
 * @author FQH
 * Create at 2020/6/22.
 * 持有多Fragment的Activity
 */
abstract class BaseFragmentActivity<B : ViewDataBinding> : BaseActivity<B>(), IFragmentCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //处理异常退出恢复情况
        if (savedInstanceState != null) {
            val fragments: List<Fragment>? = supportFragmentManager.fragments
            if (fragments != null && fragments.isNotEmpty()) {
                var showFlag = false
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                for (i in fragments.indices.reversed()) {
                    val fragment: Fragment? = fragments[i]
                    if (fragment != null) {
                        if (!showFlag) {
                            ft.show(fragments[i])
                            showFlag = true
                        } else {
                            ft.hide(fragments[i])
                        }
                    }
                }
                ft.commit()
            }
        }
    }

    /**
     * 回到第一个Fragment
     */
    override fun home() {
        while (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun initData() {
        val defaultModule: FragmentModule = getDefaultModule()
        title = defaultModule.title
        //默认fragment不用添加到回退栈中
        supportFragmentManager.beginTransaction()
            .add(getContainerViewId(), defaultModule.fragment, Const.FRAGMENT_DEFAULT)
            .commit()
    }

    abstract fun getContainerViewId(): Int

    abstract fun getDefaultModule(): FragmentModule

    override fun jump(tag: String, current: Fragment, bundle: Bundle?) {
        val transaction = supportFragmentManager.beginTransaction()
        val modules: HashMap<String, FragmentModule> = getFragmentModule()
        var target: Fragment? =
            supportFragmentManager.findFragmentByTag(tag) as Fragment?
        if (target == null) {
            target = modules[tag]!!.fragment
            transaction.hide(current)
                .add(getContainerViewId(), target, tag)
                .addToBackStack(null)
                .commit()
        } else {
            transaction.hide(current).show(target).commit()
        }
        title = modules[tag]!!.title
    }

    /**
     * 获取Fragment的信息
     *
     * @return
     */
    abstract fun getFragmentModule(): HashMap<String, FragmentModule>
}