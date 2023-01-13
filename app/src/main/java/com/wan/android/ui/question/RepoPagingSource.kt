package com.wan.android.ui.question

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wan.android.bean.ArticleEntity
import com.wan.android.http.ApiService

class RepoPagingSource(private val apiService: ApiService) :
    PagingSource<Int, ArticleEntity.DatasBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity.DatasBean> {
        return try {
            val page = params.key ?: 0 // set page 1 as default
            val pageSize = params.loadSize // page size
            val repoResponse = apiService.getQuestionList(page)
            val repoItems = repoResponse.data.datas ?: emptyList()
            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity.DatasBean>): Int? = null

}