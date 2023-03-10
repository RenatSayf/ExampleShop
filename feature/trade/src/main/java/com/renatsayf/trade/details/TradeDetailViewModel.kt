package com.renatsayf.trade.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.renatsayf.network.models.details.ProductDetails
import com.renatsayf.network.repository.INetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TradeDetailViewModel @Inject constructor(
    private val repository: INetRepository
) : ViewModel() {

    sealed class State {
        object Loading: State()
        data class DataSuccess(val details: ProductDetails): State()
        data class DataFailure(val error: String): State()
    }

    private var _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    private fun getProductDetails() {

        viewModelScope.launch {

            repository.getProductDetails().collect { res ->
                res.onSuccess { details ->
                    _state.value = State.DataSuccess(details)
                }
                res.onFailure {
                    _state.value = State.DataFailure(it.message?: "Unknown error")
                }
            }
        }
    }

    init {
        getProductDetails()
    }

}