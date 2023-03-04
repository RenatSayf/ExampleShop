package com.renatsayf.login.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatsayf.local.db.IDbRepository
import com.renatsayf.local.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: IDbRepository
) : ViewModel() {

    sealed class State {
        data class Current(
            val firstName: String = "",
            val lastName: String = "",
            val email: String = ""
        ): State()
        data class SuccessSignUp(val password: String): State()
        data class FailureSignUp(val message: String?): State()
    }

    private var _state = MutableStateFlow<State>(State.Current())
    val state: StateFlow<State> = _state

    fun setState(state: State) {
        _state.value = state
    }

    fun registration(firstName: String, lastName: String, email: String) {
        val user = User(firstName, lastName, email, "")
        viewModelScope.launch {
            repository.addUser(user).collect { res ->
                res.onSuccess { password ->
                    _state.value = State.SuccessSignUp(password)
                }
                res.onFailure {
                    _state.value = State.FailureSignUp(it.message)
                }
            }
        }
    }
}