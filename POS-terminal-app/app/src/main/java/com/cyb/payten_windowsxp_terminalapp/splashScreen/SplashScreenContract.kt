package com.cyb.payten_windowsxp_terminalapp.splashScreen

interface SplashScreenContract {
    data class SplashScreenUiState(
        val test: String = ""
    )

    sealed class SplashScreenUiEvent {
        data class OnScanQrCodeClick(val test: String): SplashScreenUiEvent()
        data class OnContinueWithoutDiscountClick(val test: String): SplashScreenUiEvent()
    }
}