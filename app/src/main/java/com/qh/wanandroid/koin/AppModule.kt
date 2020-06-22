package com.qh.wanandroid.koin

import com.qh.wanandroid.ui.ArticleViewModel
import com.qh.wanandroid.ui.collect.CollectRepository
import com.qh.wanandroid.ui.collect.CollectViewModel
import com.qh.wanandroid.ui.home.HomeRepository
import com.qh.wanandroid.ui.home.HomeViewModel
import com.qh.wanandroid.ui.integral.IntegralRepository
import com.qh.wanandroid.ui.integral.IntegralViewModel
import com.wan.login.LoginRepository
import com.wan.login.LoginViewModel
import com.qh.wanandroid.ui.myarticle.MyArticleRepository
import com.qh.wanandroid.ui.myarticle.MyArticleViewModel
import com.qh.wanandroid.ui.navigation.NavigationRepository
import com.qh.wanandroid.ui.navigation.NavigationViewModel
import com.qh.wanandroid.ui.question.QuestionRepository
import com.qh.wanandroid.ui.search.list.SearchListRepository
import com.qh.wanandroid.ui.share.ShareListRepository
import com.qh.wanandroid.ui.system.SystemListRepository
import com.qh.wanandroid.ui.system.SystemListViewModel
import com.qh.wanandroid.ui.system.act.SystemRepository
import com.qh.wanandroid.ui.tab.list.TabListRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { com.wan.login.LoginViewModel(get()) }
    viewModel { SystemListViewModel(get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { IntegralViewModel(get()) }
    viewModel { MyArticleViewModel(get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { CollectViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val repositoryModule = module {
    single { com.wan.login.LoginRepository() }
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
    single { QuestionRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)