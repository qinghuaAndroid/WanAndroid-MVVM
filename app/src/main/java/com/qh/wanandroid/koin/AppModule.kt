package com.qh.wanandroid.koin

import com.qh.wanandroid.ui.ArticleViewModel
import com.qh.wanandroid.ui.collect.CollectRepository
import com.qh.wanandroid.ui.collect.CollectViewModel
import com.qh.wanandroid.ui.home.HomeRepository
import com.qh.wanandroid.ui.home.HomeViewModel
import com.qh.wanandroid.ui.integral.IntegralRepository
import com.qh.wanandroid.ui.integral.IntegralViewModel
import com.qh.wanandroid.ui.login.LoginRepository
import com.qh.wanandroid.ui.login.LoginViewModel
import com.qh.wanandroid.ui.me.MineRepository
import com.qh.wanandroid.ui.me.MineViewModel
import com.qh.wanandroid.ui.myarticle.MyArticleRepository
import com.qh.wanandroid.ui.myarticle.MyArticleViewModel
import com.qh.wanandroid.ui.search.list.SearchListRepository
import com.qh.wanandroid.ui.share.ShareListRepository
import com.qh.wanandroid.ui.system.act.SystemRepository
import com.qh.wanandroid.ui.system.list.SystemListRepository
import com.qh.wanandroid.ui.system.list.SystemListViewModel
import com.qh.wanandroid.ui.system.navigation.NavigationRepository
import com.qh.wanandroid.ui.system.navigation.NavigationViewModel
import com.qh.wanandroid.ui.tab.list.TabListRepository
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
    viewModel { IntegralViewModel(get()) }
    viewModel { MyArticleViewModel(get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { CollectViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val repositoryModule = module {
    single { MineRepository() }
    single { LoginRepository() }
    single { SystemListRepository() }
    single { NavigationRepository() }
    single { SystemRepository() }
    single { IntegralRepository() }
    single { SearchListRepository() }
    single { MyArticleRepository() }
    single { ShareListRepository() }
    single { TabListRepository() }
    single { CollectRepository() }
    single { HomeRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)