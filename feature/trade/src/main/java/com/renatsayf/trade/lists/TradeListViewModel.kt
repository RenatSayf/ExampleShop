package com.renatsayf.trade.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatsayf.trade.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TradeListViewModel : ViewModel() {

    private var _categoryList = MutableStateFlow<Result<List<Category>>>(Result.failure(Throwable("Empty list"))).apply {
        viewModelScope.launch {
            val data = Category.data
            value = try {
                Result.success(data)
            } catch (e: Exception) {
                Result.failure(Throwable(e))
            }
        }
    }
    val categoryList: StateFlow<Result<List<Category>>> = _categoryList

}