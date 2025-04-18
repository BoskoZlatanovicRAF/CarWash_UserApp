package com.cyb.payten_windowsxp_terminalapp.payment


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.RemoteException
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
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @SuppressLint("StaticFieldLeak")
@Inject constructor(
    private val authStore: AuthStore,
    private val context : Context
): ViewModel() {

    private val _state = MutableStateFlow(PaymentContract.PaymentContractUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: PaymentContract.PaymentContractUiState.() -> PaymentContract.PaymentContractUiState) {
        _state.update(reducer)
    }

    private val events = MutableSharedFlow<PaymentContract.PaymentContactUiEvent>()
    fun setEvent(event: PaymentContract.PaymentContactUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        populateState()
        observeTokens()
        observeClearDataStore()
        observePayButton()
        observeSavedTimeButton()
    }

    private fun observeClearDataStore() {
        viewModelScope.launch {
            events
                .filterIsInstance<PaymentContract.PaymentContactUiEvent.ClearDataStore>()
                .collect {
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
private fun observePayButton() {
    viewModelScope.launch {
        events
            .filterIsInstance<PaymentContract.PaymentContactUiEvent.PayCLick>()
            .collect {
                setState {
                    copy(paymentJson = RequestJson(
                        header = Header(),
                        request = Request(
                            financial = Financial(
                                transaction = "sale",
                                id = Id(),
                                amounts = Amounts(
                                    base = String.format("%.2f", it.value.toDouble())
                                ),
                                options = Options()
                            )
                        )
                    ))
                }
                sendJsonStringToApos(state.value.paymentJson.toString())
                Log.d("--Payment JSON", state.value.paymentJson.toString())

                authStore.updateAuthData(
                    AuthData(
                        user_id = authStore.getAuthData().user_id,
                        first_name = authStore.getAuthData().first_name,
                        membership = authStore.getAuthData().membership,
                        discount = authStore.getAuthData().discount,
                        time = state.value.time // 200
                    )
                )
            }
    }
}

    private fun populateState() {
        val authData = authStore.authData.value
        //setState { copy(savedTime = 200) }
        if (authData.user_id != -1) {
            setState {
                copy(username = authData.first_name, membership = authData.membership, discount = authData.discount, savedTime = authData.time)
            }
            setState { copy(paymentJson = RequestJson(
                header = Header(),
                request = Request(
                    financial = Financial(
                        transaction = "sale",
                        id = Id(),
                        amounts = Amounts(base = "0.00"), // Početna vrednost base
                        options = Options()
                    )
                )
            ))}
        }
    }

    private fun observeTokens() {
        viewModelScope.launch {
            events
                .filterIsInstance<PaymentContract.PaymentContactUiEvent.ChangeTokenAmount>()
                .collect { event ->
                    if (event.value) {
                        setState { copy(token = state.value.token.inc()) }
                    } else {
                        if (state.value.token != 0) {
                            setState { copy(token = state.value.token.dec()) }
                        }
                    }

                    val time = if(state.value.saveTimeClicked) state.value.token * 100 + state.value.savedTime else state.value.token * 100
                    setState { copy(time = time) }

                    val basePrice = state.value.priceOfToken * state.value.token
                    val discount = basePrice * state.value.discount
                    val screenTotalPrice = basePrice - discount
                    val totalPrice = (basePrice - discount) * 120

                    setState { copy(basePrice = basePrice, discountToShow = discount, totalPrice = totalPrice, screenTotalPrice = screenTotalPrice) }

                }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Throws(
        RemoteException::class,
        InterruptedException::class
    )
    private fun sendJsonStringToApos(json: String) {
            Log.d("DATASTORE--", authStore.authData.value.toString())
            Log.d("DATASTORE--", json)
            val intent = Intent("com.payten.ecr.action")
            intent.setPackage("com.payten.paytenapos")
            intent.putExtra("ecrJson", json)
            intent.putExtra("senderIntentFilter", "senderIntentFilter")
            intent.putExtra("senderPackage", context.packageName)
            intent.putExtra("senderClass", "com.cyb.payten_windowsxp_terminalapp.MainActivity");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            context.sendBroadcast(intent)
    }


    private fun observeSavedTimeButton(){
        viewModelScope.launch {
            events.filterIsInstance<PaymentContract.PaymentContactUiEvent.SaveTimeClicked>()
                .collect{ event->
                    if (event.value) {
                        setState { copy(
                            time = state.value.time + state.value.savedTime,
                            saveTimeClicked = event.value
                        ) }
                    } else {
                        setState {
                            copy(
                                time = state.value.time - state.value.savedTime, // t: 200s // s:100s // u:300 -> 200 //
                                saveTimeClicked = event.value
                            )
                        }
                    }
                }
        }
    }
}