package com.example.payten_windowsxp_userapp.Login

import com.example.payten_windowsxp_userapp.Login.LoginScreenContract.LoginScreenUiEvent
import com.example.payten_windowsxp_userapp.Users.User

interface LoginScreenContract {
    data class LoginScreenUiState(
        val User: User? = null,
        val textFieldValue: String = "",
        val isAdmin: Boolean = false,
    )

    sealed class LoginScreenUiEvent {

    data class checkLogin(val email:String, val password: String) : LoginScreenUiEvent()
//        data class TextFieldValueChange(val value: String) : LoginScreenUiEvent()
//        data class DeletePin(val value: Boolean) : LoginScreenUiEvent()

    }
}
//
//data class EditFirstName(val firstName: String) : LoginScreenUiEvent()
//data class EditLastName(val lastName: String) : LoginScreenUiEvent()
//data class EditNickname(val nickname: String) : LoginScreenUiEvent()
//data class EditEmail(val email: String) : LoginScreenUiEvent()
//data class Reset(val reset: Boolean) : LoginScreenUiEvent()