package com.renatsayf.trade.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatsayf.local.db.IDbRepository
import com.renatsayf.local.models.User
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.Brands
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.words.Hint
import com.renatsayf.network.repository.INetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TradeListViewModel @Inject constructor(
    private val repository: INetRepository,
    private var db: IDbRepository
) : ViewModel() {

    private var _user = MutableLiveData<User?>(null)
    val user: LiveData<User?> = _user

    fun getUserData(name: String, password: String, onSuccess: (User) -> Unit = {}, onFailure: (String) -> Unit = {}) {

        viewModelScope.launch {
            val result = db.getUserAsync(name, password).await()
            result.onSuccess { user ->
                _user.value = user
                onSuccess.invoke(user)
            }
            result.onFailure {
                onFailure.invoke(it.message?: "Unknown error")
            }
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

    private var _brandsList = MutableStateFlow(Brands(emptyList())).apply {
        viewModelScope.launch {
            repository.getBrands().collect { res ->
                res.onSuccess {
                    value = it
                }
            }
        }
    }
    val brandsList: StateFlow<Brands> = _brandsList

    sealed class State {
        object Loading: State()
        data class Success(
            val flash: FlashSales,
            val latest: LatestDeals
            ): State()
        object EmptyData: State()
    }

    private var _state = MutableStateFlow<State>(State.EmptyData)
    val state: StateFlow<State> = _state

    private fun fetchFlashAndLatest() {

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

    @Suppress("OPT_IN_USAGE")
    fun getHint(): StateFlow<Hint> {

        val flow = MutableStateFlow(Hint(emptyList()))
        viewModelScope.launch {
            repository.getWordList().debounce(1000).collect { res ->
                res.onSuccess {
                    flow.value = it
                }
            }
        }
        return flow
    }

    init {
        fetchFlashAndLatest()
    }

}