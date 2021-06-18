package com.qh.wanandroid.ui

import androidx.lifecycle.viewModelScope
import com.wan.baselib.mvvm.Result
import com.wan.common.base.BaseListViewModel
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.ui.collect.CollectRepository
import com.qh.wanandroid.ui.home.HomeRepository
import com.qh.wanandroid.ui.question.QuestionRepository
import com.qh.wanandroid.ui.search.list.SearchListRepository
import com.qh.wanandroid.ui.share.ShareListRepository
import com.qh.wanandroid.ui.system.act.SystemRepository
import com.qh.wanandroid.ui.tab.list.TabListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/16.
 */
class ArticleViewModel(
    private val homeRepository: HomeRepository,
    private val shareListRepository: ShareListRepository,
    private val systemRepository: SystemRepository,
    private val searchListRepository: SearchListRepository,
    private val tabListRepository: TabListRepository,
    private val collectRepository: CollectRepository,
    private val questionRepository: QuestionRepository
) : BaseListViewModel<ArticleEntity>() {

    sealed class ArticleType {
        object Home : ArticleType()                 // 首页
        object Share : ArticleType()            // 分享
        object Project : ArticleType()        //项目
        object Collection : ArticleType()           // 收藏
        object System : ArticleType()           // 体系分类
        object Blog : ArticleType()                 // 公众号
        object Search : ArticleType()           //搜索列表
        object Question : ArticleType()           //搜索列表
    }

    private var pageNum = 0

    fun getArticles(isRefresh: Boolean) = getArticleList(ArticleType.Home, isRefresh)
    fun getShareArticle(isRefresh: Boolean) = getArticleList(ArticleType.Share, isRefresh)
    fun getSystemArticle(isRefresh: Boolean, cid: Int) = getArticleList(ArticleType.System, isRefresh, cid = cid)
    fun queryBySearchKey(isRefresh: Boolean, queryTxt: String) = getArticleList(ArticleType.Search, isRefresh, queryTxt = queryTxt)
    fun getProjectList(isRefresh: Boolean, cid: Int) = getArticleList(ArticleType.Project, isRefresh, cid = cid)
    fun getAccountList(isRefresh: Boolean, cid: Int) = getArticleList(ArticleType.Blog, isRefresh, cid = cid)
    fun getCollectData(isRefresh: Boolean) = getArticleList(ArticleType.Collection, isRefresh)
    fun getQuestionList(isRefresh: Boolean) = getArticleList(ArticleType.Question, isRefresh)

    private fun getArticleList(
        articleType: ArticleType,
        isRefresh: Boolean = false,
        cid: Int = 0,
        queryTxt: String = ""
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            if (isRefresh) {
                pageNum = 0
                emitUiModel(showLoading = true)
                emitUiModel(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) {
                when (articleType) {
                    ArticleType.Home -> homeRepository.loadArticles(pageNum)
                    ArticleType.Share -> shareListRepository.getShareArticle(pageNum)
                    ArticleType.System -> systemRepository.getSystemArticle(pageNum, cid)
                    ArticleType.Search -> searchListRepository.queryBySearchKey(pageNum, queryTxt)
                    ArticleType.Project -> tabListRepository.getProjectList(pageNum, cid)
                    ArticleType.Blog -> tabListRepository.getAccountList(cid, pageNum)
                    ArticleType.Collection -> collectRepository.getCollectData(pageNum)
                    ArticleType.Question -> questionRepository.getQuestionList(pageNum)
                }
            }
            if (result is Result.Success) {
                val articleEntity = result.data
                emitUiModel(
                    showLoading = false,
                    showSuccess = articleEntity,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                articleEntity.run {
                    if (curPage >= pageCount) {
                        emitUiModel(showEnd = true)
                        return@launch
                    }
                }
                pageNum++
            } else if (result is Result.Error) {
                emitUiModel(
                    showLoading = false,
                    showError = result.exception.message,
                    isEnableLoadMore = true
                )
            }
        }
    }

    fun collect(id:Int){
        launch {
            withContext(Dispatchers.IO){
                collectRepository.collect(id)
            }
        }
    }
}