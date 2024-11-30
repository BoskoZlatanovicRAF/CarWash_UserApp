package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Token

data class LocalState(
    val local: Local? = null,
    val fetching: Boolean = false,
    val tokens : List<Token>? = emptyList(),
    val events: Events? = null
) {
    sealed class Events {
    }
}
