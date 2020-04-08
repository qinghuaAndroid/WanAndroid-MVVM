package com.qh.wanandroid.koin

import com.qh.wanandroid.ui.login.LoginRepository
import com.qh.wanandroid.ui.login.LoginViewModel
import com.qh.wanandroid.ui.me.MineRepository
import com.qh.wanandroid.ui.me.MineViewModel
import com.qh.wanandroid.ui.system.list.SystemListRepository
import com.qh.wanandroid.ui.system.list.SystemListViewModel
import com.qh.wanandroid.ui.system.navigation.NavigationRepository
import com.qh.wanandroid.ui.system.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { MineViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SystemListViewModel(get()) }
    viewModel { NavigationViewModel(get()) }
}

val repositoryModule = module {
    single { MineRepository() }
    single { LoginRepository() }
    single { SystemListRepository() }
    single { NavigationRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)