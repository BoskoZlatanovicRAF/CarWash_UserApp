package com.example.payten_windowsxp_userapp.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.Users.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenContract.LoginScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LoginScreenContract.LoginScreenUiState.() ->
    LoginScreenContract.LoginScreenUiState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<LoginScreenContract.LoginScreenUiEvent>()
    fun setEvent(event: LoginScreenContract.LoginScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
//        addAdmin()
        observeEvents()
    }
    private fun addAdmin() {
        viewModelScope.launch {
            val user = User(
                id = 0,
                firstname = "Admin",
                lastName = "Admin",
                email = "admin@admin.com",
                phoneNumber = "123456789",
                birthdate = "01-01-2000",
                password = "admin",
                role = RoleEnum.ADMIN,
                bonusPoints = 0,
                time = "00:00"
            )
            userRepository.insertUser(user)
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is LoginScreenContract.LoginScreenUiEvent.checkLogin -> {
                        val email = event.email
                        val password = event.password
                        if (email.isEmpty() || password.isEmpty()) {
                            println("Empty fields")
                        } else {
                            val user = userRepository.getByEmailandPassword(email, password)
                            if (user == null) {
                                println("User not found")
                            } else {
                                if (user.password == password) {
                                    setState { copy(User = user) }
                                    if(user.role == RoleEnum.ADMIN){
                                        setState { copy(isAdmin = true) }
                                    }
                                } else {
                                    println("Wrong password")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}