package com.renatsayf.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatsayf.local.db.IDbRepository
import com.renatsayf.local.models.IUser
import com.renatsayf.local.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: IDbRepository
) : ViewModel() {

    sealed class State {

        object Initial: State()
        object Loading: State()
        data class DataSuccess(val user: IUser): State()
        data class Error(val error: String): State()
    }

    private var _state = MutableStateFlow<State>(State.Initial)
    val state: StateFlow<State> = _state

    fun getUserData(name: String, password: String) {

        _state.value = State.Loading
        viewModelScope.launch {
            val result = repository.getUserAsync(name, password).await()
            result.onSuccess { user ->
                _state.value = State.DataSuccess(user)
            }
            result.onFailure {
                _state.value = State.Error("No user data")
            }
        }
    }

    fun updateUserData(user: User) {

        _state.value = State.Loading
        viewModelScope.launch {
            val result = repository.updateUserAsync(user).await()
            result.onSuccess {
                _state.value = State.DataSuccess(user)
            }
        }
    }

}