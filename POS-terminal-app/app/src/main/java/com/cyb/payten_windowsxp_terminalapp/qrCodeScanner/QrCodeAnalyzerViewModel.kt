package com.cyb.payten_windowsxp_terminalapp.qrCodeScanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.payten_windowsxp_terminalapp.auth.AuthData
import com.cyb.payten_windowsxp_terminalapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrCodeAnalyzerViewModel @Inject constructor(
    private val authStore: AuthStore
): ViewModel() {
    private val _state = MutableStateFlow(QrCodeAnalyzerContract.QrCodeAnalyzerUiState())
    val state = _state.asStateFlow()
    private fun setStete(reducer: QrCodeAnalyzerContract.QrCodeAnalyzerUiState.() -> QrCodeAnalyzerContract.QrCodeAnalyzerUiState) = _state.update(reducer)

    private val events = MutableSharedFlow<QrCodeAnalyzerContract.QrCodeAnalyzerUiEvent>()
    fun setEvent(event : QrCodeAnalyzerContract.QrCodeAnalyzerUiEvent) = viewModelScope.launch{events.emit(event)}

    init {
        observeDataStore()
    }

    private fun observeDataStore() {
        viewModelScope.launch {
            events
                .filterIsInstance<QrCodeAnalyzerContract.QrCodeAnalyzerUiEvent.SetDataStore>()
                .collect { event ->
                    Log.d("event----", event.value)
                    val authData = parseAuthData(event.value)
                    authStore.updateAuthData(authData)
                }
        }
    }

    fun parseAuthData(input: String): AuthData {
        val map = input.split(";")
            .associate {
                val (key, value) = it.split(":")
                key to value
            }
        val timeString = map["TIME"] ?: throw IllegalArgumentException("TIME is missing")
        val timeParts = timeString.split("/")
        if (timeParts.size != 2) throw IllegalArgumentException("TIME format is invalid")
        val time1 = timeParts[0].toInt() * 60 + timeParts[1].toInt()
        Log.d("time1", "$time1")
        val authData = AuthData(
            user_id = map["USER_ID"]?.toInt() ?: throw IllegalArgumentException("USER_ID is missing or invalid"),
            membership = map["MEMBERSHIP"] ?: throw IllegalArgumentException("MEMBERSHIP is missing"),
            discount = map["DISCOUNT"]?.toFloat() ?: throw IllegalArgumentException("DISCOUNT is missing or invalid"),
            first_name = map["FIRST_NAME"] ?: throw IllegalArgumentException("FIRST_NAME is missing"),
            time = time1
        )

        println(authData)

        return authData
    }

}