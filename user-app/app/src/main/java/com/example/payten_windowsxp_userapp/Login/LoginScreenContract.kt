package com.example.payten_windowsxp_userapp.Login

import com.example.payten_windowsxp_userapp.Users.User

interface LoginScreenContract {
    data class LoginScreenUiState(
        val User: User? = null,
        val textFieldValue: String = "",
        val isAdmin: Boolean = false,
    )
    sealed class LoginScreenUiEvent {
        data class checkLogin(val email: String, val password: String) : LoginScreenUiEvent()
    }
}