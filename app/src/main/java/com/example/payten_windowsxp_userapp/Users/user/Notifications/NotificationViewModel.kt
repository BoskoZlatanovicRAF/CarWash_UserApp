package com.example.payten_windowsxp_userapp.Users.user.Notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Login.LoginScreenContract
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.Notifications.db.Notification
import com.example.payten_windowsxp_userapp.Users.repository.NotificationRepository
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authStore: AuthStore
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationScreenContract.NotificationUiState())
    val state = _state.asStateFlow()
    private fun setState(
        reducer: NotificationScreenContract.NotificationUiState.() ->
        NotificationScreenContract.NotificationUiState
    ) = _state.update(reducer)

    private val events = MutableSharedFlow<NotificationScreenContract.NotificationUiState>()
    fun setEvent(event: NotificationScreenContract.NotificationUiState) =
        viewModelScope.launch { events.emit(event) }

    init {
        insertNotification()
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val authData = authStore.authData.value
                val notifications = notificationRepository.getNotificationsByUser(authData.id.toInt())
                println("--dsadsa---" + notifications)
                setState { copy(notifcations = notifications) }
                println("--dsadsa---" + state.value.notifcations)
            } catch (error: Exception) {
                println("Error: ${error.message}")
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

    private fun insertNotification() {
        viewModelScope.launch {
            if(notificationRepository.getNotificationsCount() < 3){

                val notification1 = Notification(
                    id = 0,
                    title = "Gold Tier achieved!",
                    text = "You've unlocked 15% discount on all services",
                    time = "2 hours ago",
                    isRead = 0,
                    userID = "2",
                    imageId = R.drawable.medal,
                )
                val notification2 = Notification(
                    id = 0,
                    title = "Special morning price",
                    text = "Get 0.90€ per token until 10:00",
                    time = "5 hours ago",
                    isRead = 1,
                    userID = "2",
                    imageId = R.drawable.baseline_discount_24,
                )
                val notification3 = Notification(
                    id = 0,
                    title = "Your car wash completed",
                    text = "+35 bonus points added",
                    time = "Yesterday",
                    isRead = 1,
                    userID = "2",
                    imageId = R.drawable.ic_car_icon,
                )
                notificationRepository.insertAllNotification(
                    listOf(
                        notification1,
                        notification2,
                        notification3,
                    )
                )
            }
        }
    }


}
