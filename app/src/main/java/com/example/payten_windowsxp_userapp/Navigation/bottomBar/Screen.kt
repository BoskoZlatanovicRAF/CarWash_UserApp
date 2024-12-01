package com.example.payten_windowsxp_userapp.Navigation.bottomBar

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.payten_windowsxp_userapp.R

sealed interface Screen {
    val route: String

    sealed class VectorScreen(
        override val route: String,
        val unselectedIcon: ImageVector,
        val selectedIcon: ImageVector
    ) : Screen

    sealed class DrawableScreen(
        override val route: String,
        @DrawableRes val unselectedIcon: Int,
        @DrawableRes val selectedIcon: Int
    ) : Screen
    object Home : VectorScreen("userHomeScreen", Icons.Outlined.Home, Icons.Rounded.Home)
    object Location : VectorScreen("locationScreen", Icons.Outlined.LocationOn, Icons.Rounded.LocationOn)
    object QR : DrawableScreen("qrScreen",
        R.drawable.baseline_qr_code_scanner_24,
        R.drawable.baseline_qr_code_scanner_white)
    object Notifications : VectorScreen("notifications", Icons.Outlined.Notifications, Icons.Rounded.Notifications)
    object Profile : VectorScreen("userProfile", Icons.Outlined.Person, Icons.Rounded.Person)

    // Admin Screens
    object AdminHome : VectorScreen("adminHomeScreen", Icons.Outlined.Home, Icons.Rounded.Home)
    object AdminProfile : VectorScreen("userProfile", Icons.Outlined.Person, Icons.Rounded.Person)
}

