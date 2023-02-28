package com.renatsayf.trade.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.product.Product
import com.renatsayf.network.repository.NetRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class TradeListViewModel @Inject constructor(
    private val repository: NetRepository
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val repository: NetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TradeListViewModel::class.java)
            return TradeListViewModel(repository) as T
        }
    }

    private var _categoryList = MutableStateFlow<Result<List<Category>>>(Result.failure(Throwable("Empty list"))).apply {
        viewModelScope.launch {
            repository.getCategories().collect {
                value = Result.success(it)
            }
        }
    }
    val categoryList: StateFlow<Result<List<Category>>> = _categoryList

    sealed class State {
        object Loading: State()
        data class Success(val flash: FlashSales, val latest: LatestDeals): State()
        object EmptyData: State()
    }

    private var _state = MutableStateFlow<State>(State.EmptyData)
    val state: StateFlow<State> = _state

    private fun fetchData() {

        viewModelScope.launch {

            _state.value = State.Loading
            repository.getFlashSale()
                .zip(
                    other = repository.getLatestDeals(),
                    transform = { resultFlash, resultLatest ->

                        if (resultFlash.isSuccess && resultLatest.isSuccess) {
                            val flashSales = resultFlash.getOrNull()
                            flashSales?.let {
                                val latestDeals = resultLatest.getOrNull()
                                latestDeals?.let {
                                    _state.value = State.Success(flashSales, latestDeals)
                                }?: {
                                    _state.value = State.EmptyData
                                }
                            }?: {
                                _state.value = State.EmptyData
                            }
                        }
                        else {
                            _state.value = State.EmptyData
                        }
                    }
                ).collect()
        }
    }

    init {
        fetchData()
    }

}