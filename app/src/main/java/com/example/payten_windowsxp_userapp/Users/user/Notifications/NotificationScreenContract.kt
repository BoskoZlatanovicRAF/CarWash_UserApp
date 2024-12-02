package com.example.payten_windowsxp_userapp.Users.user.Notifications

import com.example.payten_windowsxp_userapp.Users.user.Notifications.db.Notification
import com.example.payten_windowsxp_userapp.Users.User

interface NotificationScreenContract {
    data class NotificationUiState(
        val fetching: Boolean = false,
        val User: User? = null,
        val notifcations: List<Notification> = emptyList(),
    )
    sealed class NotificationScreenUiEvent {
    }
}