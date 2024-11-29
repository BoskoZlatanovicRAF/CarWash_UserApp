package com.example.payten_windowsxp_userapp.Registration


data class RegisterState(
    val fatching: Boolean = false,
    val exist: Boolean = false,
    val error: Error? = null,
    val SuccesRegister: Boolean = false,
){
    sealed class Events {
        data class Register(val fullName: String, val email: String, val birthdate: String, val phoneNumber: String, val password: String) : Events()
    }
}
