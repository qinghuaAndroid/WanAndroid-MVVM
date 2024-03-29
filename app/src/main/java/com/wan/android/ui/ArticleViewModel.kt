package com.wan.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wan.android.bean.ArticleEntity
import com.wan.android.ui.collect.CollectRepository
import com.wan.android.ui.home.HomeRepository
import com.wan.android.ui.question.QuestionRepository
import com.wan.android.ui.search.list.SearchListRepository
import com.wan.android.ui.share.ShareListRepository
import com.wan.android.ui.system.act.SystemRepository
import com.wan.android.ui.tab.list.TabListRepository
import com.wan.baselib.mvvm.BaseViewModel
import com.wan.baselib.mvvm.Result
import com.wan.common.base.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/16.
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val shareListRepository: ShareListRepository,
    private val systemRepository: SystemRepository,
    private val searchListRepository: SearchListRepository,
    private val tabListRepository: TabListRepository,
    private val collectRepository: CollectRepository,
    private val questionRepository: QuestionRepository
) : BaseViewModel() {

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

    private val _uiState = MutableLiveData<ListUiState<ArticleEntity>>()
    val uiState: LiveData<ListUiState<ArticleEntity>>
        get() = _uiState

    private var pageNum = 0

    fun getArticles(isRefresh: Boolean) = getArticleList(ArticleType.Home, isRefresh)
    fun getShareArticle(isRefresh: Boolean) = getArticleList(ArticleType.Share, isRefresh)
    fun getSystemArticle(isRefresh: Boolean, cid: Int) =
        getArticleList(ArticleType.System, isRefresh, cid = cid)

    fun queryBySearchKey(isRefresh: Boolean, queryTxt: String) =
        getArticleList(ArticleType.Search, isRefresh, queryTxt = queryTxt)

    fun getProjectList(isRefresh: Boolean, cid: Int) =
        getArticleList(ArticleType.Project, isRefresh, cid = cid)

    fun getAccountList(isRefresh: Boolean, cid: Int) =
        getArticleList(ArticleType.Blog, isRefresh, cid = cid)

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
                emitUiState(showLoading = true)
                emitUiState(isEnableLoadMore = false)
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
                emitUiState(
                    showLoading = false,
                    showSuccess = articleEntity,
                    isRefresh = isRefresh,
                    isEnableLoadMore = true
                )
                articleEntity.run {
                    if (curPage >= pageCount) {
                        emitUiState(showEnd = true)
                        return@launch
                    }
                }
                pageNum++
            } else if (result is Result.Error) {
                emitUiState(
                    showLoading = false,
                    showError = result.exception.message,
                    isEnableLoadMore = true
                )
            }
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: ArticleEntity? = null,
        showEnd: Boolean = false, // 加载更多
        isRefresh: Boolean = false, // 刷新
        isEnableLoadMore: Boolean = false
    ) {
        val listUiState = ListUiState(
            showLoading,
            showError,
            showSuccess,
            showEnd,
            isRefresh,
            isEnableLoadMore
        )
        _uiState.value = listUiState
    }
}