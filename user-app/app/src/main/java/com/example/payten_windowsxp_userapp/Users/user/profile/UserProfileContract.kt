package com.example.payten_windowsxp_userapp.Users.user.profile

interface UserProfileContract {
    data class UserProfileState(
        val loading: Boolean = false,
        val loggedOut: Boolean = false,
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val dateOfBirth: String = "",
        val phoneNumber: String = "",
        val password: String = ""
    )
    sealed class UserProfileScreenUiEvent {
        class logOutClick() : UserProfileScreenUiEvent()
    }
}