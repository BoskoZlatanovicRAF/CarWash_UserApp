package com.example.payten_windowsxp_userapp.Users.user.Notifications.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotification(notification: List<Notification>)

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    suspend fun getNotificationsByUser(userId: Int): List<Notification>

    @Query("SELECT COUNT(*) FROM notifications")
    suspend fun getNotificationsCount(): Int
}