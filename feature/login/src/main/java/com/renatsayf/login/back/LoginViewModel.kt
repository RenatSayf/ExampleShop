package com.renatsayf.login.back

import androidx.lifecycle.*
import com.renatsayf.local.db.IDbRepository
import com.renatsayf.local.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IDbRepository
) : ViewModel() {

    sealed class State {
        data class Current(
            val firstName: String = "",
            val password: String = ""
        ): State()
        object SuccessLogin: State()
        data class FailureLogin(val message: String?): State()
    }

    private var _state = MutableStateFlow<State>(State.Current())
    val state: StateFlow<State> = _state

    fun setState(state: State) {
        _state.value = state
    }

    fun login(firstName: String, password: String) {

        viewModelScope.launch {
            val result = repository.getUserAsync(firstName, password).await()
            result.onSuccess {
                _state.value = State.SuccessLogin
            }
            result.onFailure { t ->
                _state.value = State.FailureLogin(t.message)
            }
        }
    }

    fun getAllUsers(): LiveData<List<User>> {

        val data = MutableLiveData<List<User>>()
        viewModelScope.launch {
            data.value = repository.getAllUsersAsync().await()
        }
        return data
    }
}