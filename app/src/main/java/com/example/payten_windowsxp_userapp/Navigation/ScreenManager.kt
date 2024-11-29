package com.example.payten_windowsxp_userapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.payten_windowsxp_userapp.Login.logIn
import com.example.payten_windowsxp_userapp.Registration.registerScreen

@Composable
fun ScreenManager() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        logIn(
            route = "login",
            onUserClick = {
                if (it.equals("registerScreen")) {
                    navController.navigate(route = "registerScreen")
                } else if (it.equals("admin")){
                    navController.navigate(route = "admin")
                }
            }
        )
        registerScreen(
            route = "registerScreen",
            onItemClick = {
                navController.navigate(route = "login")
            },
            onLoginClick = {
                navController.navigate(route = "login")
            }
        )
    }
}