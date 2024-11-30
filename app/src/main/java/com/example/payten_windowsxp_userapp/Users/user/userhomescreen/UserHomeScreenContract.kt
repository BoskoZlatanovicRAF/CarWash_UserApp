package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

import com.example.payten_windowsxp_userapp.Users.user.db.Transaction

interface UserHomeScreenContract {

    data class UserHomeScreenState(
        val loading: Boolean = false,
        val name: String = "",
        val bonusPoints: Long = 0,
        val transactions: List<Transaction> = emptyList()
    )

    sealed class UserHomeScreenEvent {

    }
}