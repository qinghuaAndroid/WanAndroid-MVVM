package com.wan.android.ui.question

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.wan.android.R
import com.wan.android.adapter.FooterAdapter
import com.wan.android.adapter.QuestionAdapter
import com.wan.android.databinding.ActivityQuestionBinding
import com.wan.android.ui.ArticleViewModel
import com.wan.baselib.ext.showToast
import com.wan.baselib.mvvm.BaseVMActivity
import com.wan.common.arouter.ArouterPath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author cy
 * Create at 2020/4/15.
 */
@AndroidEntryPoint
@Route(path = ArouterPath.ACTIVITY_QUESTION)
class QuestionActivity : BaseVMActivity<ArticleViewModel, ActivityQuestionBinding>() {

    private val questionViewModel by viewModels<QuestionViewModel>()
    private val questionAdapter by lazy { QuestionAdapter() }

    override fun subscribeUi() {

    }

    override fun getLayoutId(): Int = R.layout.activity_question

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        title = getString(R.string.question)
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener { questionAdapter.refresh() }
    }

    private fun initRecyclerView() {
        binding.rvQuestionList.layoutManager = LinearLayoutManager(this)
        binding.rvQuestionList.adapter =
            questionAdapter.withLoadStateFooter(FooterAdapter { questionAdapter.retry() })
        questionAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.swipeRefresh.isRefreshing = false
                    showToast("Load Error: ${state.error.message}")
                }
            }
        }
    }

    override fun loadData() {
        lifecycleScope.launch {
            questionViewModel.getPagingData().collectLatest { pagingData ->
                questionAdapter.submitData(pagingData)
            }
        }
    }
}