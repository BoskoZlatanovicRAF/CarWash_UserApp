package com.cyb.payten_windowsxp_terminalapp.washingScreen

interface WashingScreenContract {
    data class WashingScreenUiState(
        val startWashing: Boolean = false,
        val time: Int = 0,
        val endWashing: Boolean = false,
        val switchToSplash: Boolean = false,
        val readyForThanks: Boolean = false
    )

    sealed class WashingScreenUiEvent {
        data class StartWashingChange(val value: Boolean): WashingScreenUiEvent()
        data class SwitchToSplash(val value: Boolean): WashingScreenUiEvent()
        data class SwitchToThanks(val value: Boolean): WashingScreenUiEvent()
    }
}