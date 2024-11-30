package com.example.payten_windowsxp_userapp.Users.repository

import com.example.payten_windowsxp_userapp.Users.user.db.Transaction
import com.example.payten_windowsxp_userapp.db.AppDatabase
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val database: AppDatabase
) {

    suspend fun insertTransaction(transaction: Transaction) {
        database.transactionDao().insertTransaction(transaction)
    }

    suspend fun getTransactionsForUser(userId: Long): List<Transaction> {
        return database.transactionDao().getTransactionsForUser(userId)
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return database.transactionDao().getAllTransactions()
    }
}