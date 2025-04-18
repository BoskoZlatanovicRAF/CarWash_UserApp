package com.cyb.payten_windowsxp_terminalapp.terminalNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cyb.payten_windowsxp_terminalapp.failScreen.failScreen
import com.cyb.payten_windowsxp_terminalapp.payment.payment
import com.cyb.payten_windowsxp_terminalapp.qrCodeScanner.qrCodeAnalyzer
import com.cyb.payten_windowsxp_terminalapp.receiver.TxStatus
import com.cyb.payten_windowsxp_terminalapp.splashScreen.splashScreen
import com.cyb.payten_windowsxp_terminalapp.washingScreen.washingScreens

@Composable
fun TerminalNavigation(
    txStatus: TxStatus?,
    startDestination: String = "splash_screen"
) {
    val navController = rememberNavController()

    LaunchedEffect(txStatus) {
        if(txStatus != null) {
            if (txStatus.success) {
                navController.navigate("washing")
            } else {
                navController.navigate("fail_screen")
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        splashScreen(
            route = "splash_screen",
            onUserClick = {
                if(it.equals("qr"))
                    navController.navigate(route = "qr_code")
                else
                    navController.navigate(route = "payment")
            }
        )
        qrCodeAnalyzer(
            route = "qr_code",
            onClose = {
                navController.navigateUp()
            },
            navigate = {
                navController.navigate(route = "payment")
            }
        )
        payment(
            route = "payment",
            onUserClick = {
                navController.navigate(route = "washing")
            }
        )
        washingScreens(
            route = "washing",
            onUserClick = {
                navController.navigate(route = "splash_screen")
            }
        )

        failScreen(
            route = "fail_screen",
            onTryAgainClick = {
                navController.navigate(route = "splash_screen")
            }
        )
    }
}