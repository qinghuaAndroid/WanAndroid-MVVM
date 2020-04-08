package com.qh.wanandroid.ui.system.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.NavigationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/8.
 */
class NavigationViewModel(private val mRepository: NavigationRepository) : BaseViewModel() {

    private val _navigationData = MutableLiveData<MutableList<NavigationEntity>>()
    private val _errorData = MutableLiveData<String>()

    val navigationData: LiveData<MutableList<NavigationEntity>>
        get() = _navigationData

    val errorData: LiveData<String>
        get() = _errorData

    fun getNavigation() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { mRepository.getNavigation() }
            if (result is Result.Success) _navigationData.value = result.data
            else if (result is Result.Error) _errorData.value = result.exception.message
        }
    }
}