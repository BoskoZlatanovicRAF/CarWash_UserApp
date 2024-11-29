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
                println("------------------------------User clicked on: $it")
                if (it.equals("admin")) {
//                    navController.navigate(route = "admin_home_screen")
                    navController.navigate(route = "registerScreen")
                } else {
//                    navController.navigate(route = "user_home_screen")
                    navController.navigate(route = "registerScreen")
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