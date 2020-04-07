package com.qh.wanandroid.ui.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devlibrary.mvvm.BaseViewModel
import com.example.devlibrary.mvvm.Result
import com.qh.wanandroid.bean.IntegralEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author FQH
 * Create at 2020/4/7.
 */
class MineViewModel(private val repository: MineRepository) : BaseViewModel() {

    private val _mIntegralData: MutableLiveData<IntegralEntity> = MutableLiveData()
    val integralData: LiveData<IntegralEntity>
        get() = _mIntegralData

    fun getIntegral() {
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.IO){repository.getIntegral()}
            if (result is Result.Success) _mIntegralData.value = result.data
        }
    }
}