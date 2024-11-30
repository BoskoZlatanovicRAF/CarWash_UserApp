package com.example.payten_windowsxp_userapp.Users.user.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE user_id = :userId")
    suspend fun getTransactionsForUser(userId: Long): List<Transaction>

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTransactionsCount(): Int
}