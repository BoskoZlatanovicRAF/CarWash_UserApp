package com.example.payten_windowsxp_userapp.Users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "users"
)
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "first_name")
    val firstname: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val birthdate: String,
    val password: String,
    val role: RoleEnum,
    val bonusPoints: Long,
    val time: String

)