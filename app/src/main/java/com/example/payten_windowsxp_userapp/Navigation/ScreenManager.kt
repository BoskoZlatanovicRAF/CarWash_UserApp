package com.example.payten_windowsxp_userapp.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.payten_windowsxp_userapp.Login.logIn
import com.example.payten_windowsxp_userapp.Navigation.bottomBar.BottomNavigationBar
import com.example.payten_windowsxp_userapp.Registration.registerScreen
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.EditLocalScreen.editLocalScreen
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.localScreen
import com.example.payten_windowsxp_userapp.Users.Admin.adminHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.QR.generateQRScreen
import com.example.payten_windowsxp_userapp.Users.user.locationScreen.locationScreen
import com.example.payten_windowsxp_userapp.Users.user.profile.userProfileScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.userHomeScreen

@Composable
fun ScreenManager() {
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route
            if(currentRoute != "login" && currentRoute != "registerScreen"){
              BottomNavigationBar(navController, currentRoute)
            }
        }
    ){ paddingValue ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValue)
        ) {
            logIn(
                route = "login",
                onUserClick = {
                    when (it) {
                        "registerScreen" -> navController.navigate(route = "registerScreen")
                        "adminHomeScreen" -> navController.navigate(route = "adminHomeScreen")
                        "userHomeScreen" -> navController.navigate(route = "userHomeScreen")
                        else -> navController.navigate(route = "login")
                    }
                }
            )
            registerScreen(
                route = "registerScreen",
                onItemClick = {
                    navController.navigate(route = "userHomeScreen")
                },
                onLoginClick = {
                    navController.navigate(route = "login")
                }
            )
            userHomeScreen(
                route = "userHomeScreen",
                onBonusClick = {
                    navController.navigate(route = "")//dodaj rutu za points screen
                },
                onQrClick = {
                    navController.navigate(route = "qrScreen")
                }
            )
            generateQRScreen(
                route = "qrScreen",
                onBackClick = {
                    navController.navigateUp();
                }
            )
            locationScreen(
                route = "locationScreen"
            )
            userProfileScreen(
                route = "userProfile",
                onBackClick = {
                    navController.navigateUp();
                },
                onEditClick = {

                }
            )
            adminHomeScreen(
                route = "adminHomeScreen",
                onItemClick = {
                    navController.navigate(route = "localScreen/${it}")
                },
            )
            localScreen(
                route = "localScreen/{localId}",
                onBackClick = {
                    navController.navigateUp();
                }
            )
            editLocalScreen(
                route = "editLocalScreen/{editLocalId}",
            )
        }
    }

}

inline val SavedStateHandle.localId: String
    get() = checkNotNull(get("localId")) { "localId is mandatory" }

inline val SavedStateHandle.editLocalId: String
    get() = checkNotNull(get("editLocalId")) { "editLocalId is mandatory" }