package com.cyb.payten_windowsxp_terminalapp.failScreen

interface FailScreenContract {

    data class FailScreenState(
        val test: String = ""
    )

    sealed class FailScreenUiEvent {
        data class TryAgainClickedEvent(val value: String): FailScreenUiEvent()
    }
}