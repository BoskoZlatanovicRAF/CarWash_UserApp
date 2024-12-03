package com.example.payten_windowsxp_userapp.Users.user.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions"
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "bonus_points") val bonusPoints: Int,
    @ColumnInfo(name = "transaction_date") val date: String
)
