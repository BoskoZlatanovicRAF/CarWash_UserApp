package com.cyb.payten_windowsxp_terminalapp.auth

import kotlinx.serialization.Serializable
//USER_ID:2;MEMBERSHIP:Gold;DISCOUNT:0.1;FIRST_NAME:Ilija

@Serializable
data class AuthData(
    val user_id: Int,
    val membership: String,
    val discount: Float,
    val first_name: String,
    val time: Int
)
