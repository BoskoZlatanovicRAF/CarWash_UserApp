package com.example.payten_windowsxp_userapp.Users.Admin

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.LocalUI
import com.example.payten_windowsxp_userapp.Users.User

data class AdminState(
    val fetching: Boolean = false,
    val User: User? = null,
    val locals: List<LocalUI> = emptyList(),
) {

}