package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "tokens"
)
data class Token (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val locationID: String,
    val timeStart: String,
    val timeEnd: String,
    val price: Int,
)