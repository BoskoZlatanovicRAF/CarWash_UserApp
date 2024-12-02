package com.example.payten_windowsxp_userapp.Users.repository

import com.example.payten_windowsxp_userapp.Users.user.Notifications.db.Notification
import com.example.payten_windowsxp_userapp.db.AppDatabase
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val database: AppDatabase
) {

    suspend fun insertNotification(notification: Notification) {
        database.notificationDao().insertNotification(notification)
    }
    suspend fun insertAllNotification(notification: List<Notification>) {
        database.notificationDao().insertAllNotification(notification)
    }

    suspend fun getNotificationsByUser(userId: Int): List<Notification> {
        return database.notificationDao().getNotificationsByUser(userId)
    }

    suspend fun getNotificationsCount(): Int {
        return database.notificationDao().getNotificationsCount()
    }

}