package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.repository.TransactionRepository
import com.example.payten_windowsxp_userapp.Users.user.locationScreen.CarWashLocation
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenContract.UserHomeScreenEvent
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenContract.UserHomeScreenState
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val _navigationEvent = MutableSharedFlow<String>()

    fun setEvent(event: UserHomeScreenEvent) = viewModelScope.launch { events.emit(event)}

    private val carWashLocations = listOf(
        CarWashLocation(44.837411, 20.402724, "Novi Beograd"),
        CarWashLocation(44.82414368294484, 20.39677149927324, "Stara Pazova "),
        CarWashLocation(44.800371, 20.456867, "Stari Grad"),
        CarWashLocation(44.792307, 20.491119, "Vracar Wash"),
        CarWashLocation(44.774992, 20.476667, "Vozdovac Wash"),
        CarWashLocation(44.778358, 20.415154, "Banovo Brdo Wash")
    )



    init{
        loadFromDataStore()
        observeEvents()
    }


    private fun observeEvents() {
        viewModelScope.launch {
            events.collectLatest { event ->
                when (event) {
                    is UserHomeScreenEvent.UpdateCurrentLocation -> {
                        updateNearestCarWash(event.latitude, event.longitude)
                    }
                    is UserHomeScreenEvent.NavigateToCarWash -> {
                        val currentLocation = state.value.nearestCarWash?.let { carWash ->
                            "locationScreen/${event.carWash.latitude}/${event.carWash.longitude}"
                        } ?: "locationScreen"
                        _navigationEvent.emit(currentLocation)
                    }
                }
            }
        }
    }

    private fun updateNearestCarWash(currentLat: Double, currentLon: Double) {
        var nearestCarWash: CarWashLocation? = null
        var shortestDistance = Float.MAX_VALUE

        carWashLocations.forEach { carWash ->
            val distance = calculateDistance(
                currentLat, currentLon,
                carWash.latitude, carWash.longitude
            )
            if (distance < shortestDistance) {
                shortestDistance = distance
                nearestCarWash = carWash
            }
        }
        setState {
            copy(
                nearestCarWash = nearestCarWash,
                distanceToNearestCarWash = shortestDistance
            )
        }
    }

    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
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