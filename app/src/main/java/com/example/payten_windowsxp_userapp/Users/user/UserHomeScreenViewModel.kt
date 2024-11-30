package com.example.payten_windowsxp_userapp.Users.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.repository.TransactionRepository
import com.example.payten_windowsxp_userapp.Users.user.UserHomeScreenContract.UserHomeScreenEvent
import com.example.payten_windowsxp_userapp.Users.user.UserHomeScreenContract.UserHomeScreenState
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeScreenViewModel @Inject constructor(
    private val authStore: AuthStore,
    private val transactionRepository: TransactionRepository
) : ViewModel(){
    private val _state = MutableStateFlow(UserHomeScreenState())
    val state = _state.asStateFlow()

    private fun setState(reducer: UserHomeScreenState.() -> UserHomeScreenState) = _state.update(reducer)
    private val events = MutableSharedFlow<UserHomeScreenEvent>()

    fun setEvent(event: UserHomeScreenEvent) = viewModelScope.launch { events.emit(event)}

    init{
        loadFromDataStore()
    }

    private fun loadFromDataStore(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            val authData = authStore.authData.value
            if(authData.firstname.isNotEmpty()){
                val userTransactions = transactionRepository.getTransactionsForUser(authData.id)
                setState {
                    copy(loading = false,
                    name = authData.firstname,
                    bonusPoints = authData.bonusPoints,
                    transactions = userTransactions
                    )
                }
            } else {
                setState { copy(loading = false) }
            }
        }
    }
}