package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.EditLocalScreen

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local

data class EditLocalState(
    val fetching: Boolean = false,
    val local: Local? = null,
) {
    sealed class Events {
    }
}