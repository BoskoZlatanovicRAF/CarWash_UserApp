package com.example.payten_windowsxp_userapp.Users.user.Notifications.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications"
)
data class Notification (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val text: String,
    val time: String,
    val isRead: Int,
    val userID: String,
    val imageId: Int
)