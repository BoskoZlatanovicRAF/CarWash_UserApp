package com.cyb.payten_windowsxp_terminalapp.qrCodeScanner

interface QrCodeAnalyzerContract {
    data class QrCodeAnalyzerUiState(
            var code: String = ""
    )

    sealed class QrCodeAnalyzerUiEvent {
        data class SetDataStore(val value: String) : QrCodeAnalyzerUiEvent()
    }
}