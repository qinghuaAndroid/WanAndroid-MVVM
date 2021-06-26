package com.wan.android.koin

import com.wan.android.ui.ArticleViewModel
import com.wan.android.ui.collect.CollectRepository
import com.wan.android.ui.collect.CollectViewModel
import com.wan.android.ui.home.HomeRepository
import com.wan.android.ui.home.HomeViewModel
import com.wan.android.ui.integral.IntegralRepository
import com.wan.android.ui.integral.IntegralViewModel
import com.wan.login.viewmodel.LoginViewModel
import com.wan.android.ui.myarticle.MyArticleRepository
import com.wan.android.ui.myarticle.MyArticleViewModel
import com.wan.android.ui.navigation.NavigationRepository
import com.wan.android.ui.navigation.NavigationViewModel
import com.wan.android.ui.question.QuestionRepository
import com.wan.android.ui.search.list.SearchListRepository
import com.wan.android.ui.share.ShareListRepository
import com.wan.android.ui.system.SystemListRepository
import com.wan.android.ui.system.SystemListViewModel
import com.wan.android.ui.system.act.SystemRepository
import com.wan.android.ui.tab.list.TabListRepository
import com.wan.login.repository.LoginRepository
import com.wan.login.repository.RegisterRepository
import com.wan.login.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { SystemListViewModel(get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { IntegralViewModel(get()) }
    viewModel { MyArticleViewModel(get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { CollectViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val repositoryModule = module {
    single { LoginRepository() }
    single { RegisterRepository() }
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