package com.cyb.payten_windowsxp_terminalapp.washingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.payten_windowsxp_terminalapp.auth.AuthData
import com.cyb.payten_windowsxp_terminalapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WashingScreenViewModel @Inject constructor(
    private val authStore: AuthStore
): ViewModel() {
    private val _state = MutableStateFlow(WashingScreenContract.WashingScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: WashingScreenContract.WashingScreenUiState.() ->
    WashingScreenContract.WashingScreenUiState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<WashingScreenContract.WashingScreenUiEvent>()
    fun setEvent(event: WashingScreenContract.WashingScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        populateState()
        observeStartWashing()
        observeSwitchToThanks()
        observeSwitchToSplash()
    }

    private fun observeSwitchToSplash() {
        viewModelScope.launch {
            events
                .filterIsInstance<WashingScreenContract.WashingScreenUiEvent.SwitchToSplash>()
                .collect { event ->
                    authStore.updateAuthData(
                        AuthData(
                            user_id = -1,
                            first_name = "",
                            membership = "",
                            discount = 0f,
                            time = 0
                        )
                    )
                    delay(3000L)
                    setState { copy(switchToSplash = true) }
                }
        }
    }

    private fun observeSwitchToThanks() {
        viewModelScope.launch {
            events
                .filterIsInstance<WashingScreenContract.WashingScreenUiEvent.SwitchToThanks>()
                .collect { event ->
                    setState { copy(readyForThanks = event.value) }
                }
        }
    }

    private fun populateState() {
        viewModelScope.launch {
            val time = authStore.getAuthData().time.toInt()
            setState { copy(time = time) }
        }
    }

    private fun observeStartWashing() {
        viewModelScope.launch {
            events
                .filterIsInstance<WashingScreenContract.WashingScreenUiEvent.StartWashingChange>()
                .collect { event ->
                    setState { copy(startWashing = event.value) }
                    startTimer()
                }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            for (seconds in state.value.time downTo 0) {
                delay(1000L)
                setState { copy(time = seconds) }
            }
            // Kada vreme istekne, označi QR kod kao nevažeći
            setState { copy(readyForThanks = true) }
        }
    }
}