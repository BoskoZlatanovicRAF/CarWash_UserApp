package com.example.payten_windowsxp_userapp.Users.user.QR

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenViewModel


fun NavGraphBuilder.generateQRScreen(
    route: String,
    onBackClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val generateQRViewModel: GenerateQRViewModel = hiltViewModel(navBackStackEntry)
    val state = generateQRViewModel.state.collectAsState()
    GenerateQRScreen(
        state = state.value
    )
}

@Composable
fun GenerateQRScreen(
    state: GenerateQRContract.GenerateQRState,
) {

    if (state.loading) {
        // Prikaz loadera
        CircularProgressIndicator()
    } else {
        state.qrBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.fillMaxSize()
            )
        } ?: Text("QR Code nije generisan.")
    }
}