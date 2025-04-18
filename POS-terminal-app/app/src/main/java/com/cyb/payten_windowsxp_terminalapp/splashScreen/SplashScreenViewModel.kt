package com.cyb.payten_windowsxp_terminalapp.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.payten_windowsxp_terminalapp.auth.AuthData
import com.cyb.payten_windowsxp_terminalapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authStore: AuthStore
): ViewModel() {
    private val _state = MutableStateFlow(SplashScreenContract.SplashScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: SplashScreenContract.SplashScreenUiState.() ->
    SplashScreenContract.SplashScreenUiState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<SplashScreenContract.SplashScreenUiEvent>()
    fun setEvent(event: SplashScreenContract.SplashScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        initDataStore()
    }

    private fun initDataStore() {
        viewModelScope.launch {
            authStore.updateAuthData(
                AuthData(
                    user_id = -1,
                    first_name = "",
                    membership = "",
                    discount = 0f,
                    time = 0
                )
            )
        }
    }
}