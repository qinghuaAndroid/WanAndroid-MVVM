package com.qh.wanandroid.ui.question

import com.wan.baselib.mvvm.BaseRepository
import com.wan.baselib.mvvm.Result
import com.qh.wanandroid.bean.ArticleEntity
import com.qh.wanandroid.http.HttpHelper

/**
 * @author FQH
 * Create at 2020/4/15.
 */
class QuestionRepository : BaseRepository() {

    suspend fun getQuestionList(pageNum:Int):Result<ArticleEntity> {
        return safeApiCall { requestQuestionList(pageNum) }
    }

    private suspend fun requestQuestionList(pageNum: Int):Result<ArticleEntity> {
        return executeResponse(HttpHelper.apiService.getQuestionList(pageNum))
    }
}