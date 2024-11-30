package com.example.payten_windowsxp_userapp.auth

import androidx.room.ColumnInfo
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    val id: Long,
    val firstname: String,
    val lastName: String,
    val email: String,
    val bonusPoints: Long,
    val time: String
)
