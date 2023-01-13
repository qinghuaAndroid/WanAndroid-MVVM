package com.wan.android.ui.question

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wan.android.bean.ArticleEntity
import com.wan.android.http.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author cy
 * Create at 2020/4/15.
 */
class QuestionRepository @Inject constructor(private val apiService: ApiService) {

    private val pageSize = 10

    fun getPagingData(): Flow<PagingData<ArticleEntity.DatasBean>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = { RepoPagingSource(apiService) }
        ).flow
    }
}