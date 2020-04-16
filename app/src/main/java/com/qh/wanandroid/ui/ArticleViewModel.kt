package com.qh.wanandroid.ui

import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.base.BaseListViewModel
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.ui.search.list.SearchListRepository
import com.qh.wanandroid.ui.share.ShareListRepository
import com.qh.wanandroid.ui.system.act.SystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/16.
 */
class ArticleViewModel(
    private val shareListRepository: ShareListRepository,
    private val systemRepository: SystemRepository,
    private val searchListRepository: SearchListRepository
) : BaseListViewModel<ArticleEntity>() {

    sealed class ArticleType {
        //        object Home : ArticleType()                 // 首页
        object shareList : ArticleType()            // 分享

        //        object Square : ArticleType()               // 广场
//        object LatestProject : ArticleType()        // 最新项目
//        object ProjectDetailList : ArticleType()    // 项目列表
//        object Collection : ArticleType()           // 收藏
        object System : ArticleType()           // 体系分类

        //        object Blog : ArticleType()                 // 公众号
        object searchList : ArticleType()           //搜索列表
    }

    private var pageNum = 0

    fun getShareArticle(isRefresh: Boolean) = getArticleList(ArticleType.shareList, isRefresh)
    fun getSystemArticle(isRefresh: Boolean, cid: Int) = getArticleList(ArticleType.System, isRefresh, cid = cid)
    fun getSystemArticle(isRefresh: Boolean, queryTxt: String) = getArticleList(ArticleType.searchList, isRefresh, queryTxt = queryTxt)

    private fun getArticleList(
        articleType: ArticleType,
        isRefresh: Boolean = false,
        cid: Int = 0,
        queryTxt: String = ""
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiModel(showLoading = true)
            if (isRefresh) {
                pageNum = 0
                emitUiModel(isEnableLoadMore = false)
            }
            val result = withContext(Dispatchers.IO) {
                when (articleType) {
                    ArticleType.shareList -> shareListRepository.getShareArticle(pageNum)
                    ArticleType.System -> systemRepository.getSystemArticle(pageNum, cid)
                    ArticleType.searchList -> searchListRepository.queryBySearchKey(
                        pageNum,
                        queryTxt
                    )
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
}