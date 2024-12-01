package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

import android.location.Location
import com.example.payten_windowsxp_userapp.Users.user.db.Transaction
import com.example.payten_windowsxp_userapp.Users.user.locationScreen.CarWashLocation

interface UserHomeScreenContract {

    data class UserHomeScreenState(
        val loading: Boolean = false,
        val name: String = "",
        val bonusPoints: Long = 0,
        val transactions: List<Transaction> = emptyList(),
        val nearestCarWash: CarWashLocation? = null,
        val distanceToNearestCarWash: Float = 0f // u metrima
    )



    sealed class UserHomeScreenEvent {
        data class UpdateCurrentLocation(val latitude: Double, val longitude: Double) : UserHomeScreenEvent()
    }
}