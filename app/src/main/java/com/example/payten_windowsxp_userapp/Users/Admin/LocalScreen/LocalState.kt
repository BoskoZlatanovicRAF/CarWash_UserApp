package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local

data class LocalState(
    val local: Local? = null,
    val fetching: Boolean = false,
    val events: Events? = null
) {
    sealed class Events {
    }
}
