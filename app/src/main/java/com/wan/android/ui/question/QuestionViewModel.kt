package com.wan.android.ui.question

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wan.android.bean.ArticleEntity
import com.wan.baselib.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val mRepository: QuestionRepository) :
    BaseViewModel() {

    fun getPagingData(): Flow<PagingData<ArticleEntity.DatasBean>> {
        return mRepository.getPagingData().cachedIn(viewModelScope)
    }

}