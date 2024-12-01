package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.repository.TransactionRepository
import com.example.payten_windowsxp_userapp.Users.user.locationScreen.CarWashLocation
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenContract.UserHomeScreenEvent
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenContract.UserHomeScreenState
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserHomeScreenViewModel @Inject constructor(
    private val authStore: AuthStore,
    private val transactionRepository: TransactionRepository
) : ViewModel(){
    private val _state = MutableStateFlow(UserHomeScreenState())
    val state = _state.asStateFlow()

    private val carWashLocations = listOf(
        CarWashLocation(44.837411, 20.402724, "Car Wash 1"),
        CarWashLocation(44.82414368294484, 20.39677149927324, "Car Wash 2"),
        CarWashLocation(44.805052, 20.462605, "WindowsWash"), // vidljiva perionica
    )

    private fun setState(reducer: UserHomeScreenState.() -> UserHomeScreenState) = _state.update(reducer)
    private val events = MutableSharedFlow<UserHomeScreenEvent>()


    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init{
        loadFromDataStore()
        observeEvents()
    }

    fun setEvent(event: UserHomeScreenEvent) = viewModelScope.launch { events.emit(event)}

    private fun observeEvents() {
        viewModelScope.launch {
            events.collectLatest { event ->
                when (event) {
                    is UserHomeScreenEvent.UpdateCurrentLocation -> {
                        updateNearestCarWash(event.latitude, event.longitude)
                    }
                    is UserHomeScreenEvent.NavigateToCarWash -> {
                        // Kreiramo URI za navigaciju sa trenutnom lokacijom i destinacijom
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
//            Log.d("UserHomeScreenViewModel", "Distance to shortest $shortestDistance: $distance")
        }



        setState {
            copy(
                nearestCarWash = nearestCarWash,
                distanceToNearestCarWash = shortestDistance
            )
        }
//        Log.d("UserHomeScreenViewModel", "Nearest car wash: ${state.value.nearestCarWash} ${state.value.distanceToNearestCarWash}")
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