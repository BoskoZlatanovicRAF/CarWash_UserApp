package com.cyb.payten_windowsxp_terminalapp.failScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.payten_windowsxp_terminalapp.auth.AuthData
import com.cyb.payten_windowsxp_terminalapp.auth.AuthStore
import com.cyb.payten_windowsxp_terminalapp.washingScreen.WashingScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FailScreenViewModel @Inject constructor(
    private val authStore: AuthStore
) : ViewModel() {
    private val _state = MutableStateFlow(FailScreenContract.FailScreenState())
    val state = _state.asStateFlow()
    private fun setState(
        reducer: FailScreenContract.FailScreenState.() ->
        FailScreenContract.FailScreenState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<FailScreenContract.FailScreenUiEvent>()
    fun setEvent(event: FailScreenContract.FailScreenUiEvent) =
        viewModelScope.launch { events.emit(event) }

    init {
        observeTryAgainClick()
    }

    private fun observeTryAgainClick() {
        viewModelScope.launch {
            events
                .filterIsInstance<FailScreenContract.FailScreenUiEvent>()
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
                }
        }
    }
}