package com.example.payten_windowsxp_userapp.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.Users.repository.TransactionRepository
import com.example.payten_windowsxp_userapp.Users.repository.UserRepository
import com.example.payten_windowsxp_userapp.Users.user.db.Transaction
import com.example.payten_windowsxp_userapp.auth.AuthData
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val authStore: AuthStore
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenContract.LoginScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(
        reducer: LoginScreenContract.LoginScreenUiState.() ->
        LoginScreenContract.LoginScreenUiState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<LoginScreenContract.LoginScreenUiEvent>()
    fun setEvent(event: LoginScreenContract.LoginScreenUiEvent) =
        viewModelScope.launch { events.emit(event) }

    init {
        addUsers()
        addTransactions()
        observeEvents()
    }

    private fun addTransactions() {
        viewModelScope.launch {
            if (transactionRepository.getTransactionsCount() < 3) {
                val transactions = listOf(
                    Transaction(
                        id = 0,
                        userId = 2,
                        description = "Car Wash Box 1",
                        bonusPoints = 20,
                        date = "30-11-2024"
                    ),
                    Transaction(
                        id = 0,
                        userId = 2,
                        description = "Car Wash Box 2",
                        bonusPoints = 50,
                        date = "01-12-2024"
                    ),
                    Transaction(
                        id = 0,
                        userId = 2,
                        description = "Car Wash Box 3",
                        bonusPoints = 30,
                        date = "02-12-2024"
                    )
                )
                transactions.forEach { transaction ->
                    transactionRepository.insertTransaction(transaction)
                }
            }
        }
    }

    private fun addUsers() {
        viewModelScope.launch {
            if (userRepository.getUserCount() < 1) {
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
            if (userRepository.getUserCount() < 2) {
                val user = User(
                    id = 2,
                    firstname = "User",
                    lastName = "Useric",
                    email = "user@user.com",
                    phoneNumber = "123456789",
                    birthdate = "01-01-2000",
                    password = "user",
                    role = RoleEnum.USER,
                    bonusPoints = 0,
                    time = "5:10"
                )
                userRepository.insertUser(user)
            }
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
                                    if (user.role == RoleEnum.ADMIN) {
                                        setState { copy(isAdmin = true) }
                                    }
                                    authStore.updateAuthData(
                                        AuthData(
                                            id = user.id,
                                            firstname = user.firstname,
                                            lastName = user.lastName,
                                            email = user.email,
                                            bonusPoints = user.bonusPoints,
                                            time = user.time,
                                            role = user.role
                                        )
                                    )
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