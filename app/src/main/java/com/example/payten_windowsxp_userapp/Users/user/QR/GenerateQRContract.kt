package com.example.payten_windowsxp_userapp.Users.user.QR

import android.graphics.Bitmap

interface GenerateQRContract {

    data class GenerateQRState (
        val loading: Boolean = false,
        val userId: Long = 0,
        val firstName: String = "",
        val membership: String = "Gold",
        val discount: Double = 0.1,
        val timeRemaining: Int = 300,
        val qrBitmap: Bitmap? = null,
        val qrExpired: Boolean = false
    )
}