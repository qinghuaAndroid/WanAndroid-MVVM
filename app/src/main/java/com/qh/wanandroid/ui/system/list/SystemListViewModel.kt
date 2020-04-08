package com.qh.wanandroid.ui.system.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.SystemListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class SystemListViewModel(private val mRepository: SystemListRepository) : BaseViewModel() {

    private val _systemListData = MutableLiveData<MutableList<SystemListEntity>>()
    private val _errorData = MutableLiveData<String>()

    val systemListData: LiveData<MutableList<SystemListEntity>>
        get() = _systemListData

    val errorData: LiveData<String>
        get() = _errorData

    fun getSystemList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getSystemList() }
            if (result is Result.Success) _systemListData.value = result.data
            else if (result is Result.Error) _errorData.value = result.exception.message
        }
    }
}