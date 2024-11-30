package com.example.payten_windowsxp_userapp.Users.user.QR

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenViewModel
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular


fun NavGraphBuilder.generateQRScreen(
    route: String,
    onBackClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val generateQRViewModel: GenerateQRViewModel = hiltViewModel(navBackStackEntry)
    val state = generateQRViewModel.state.collectAsState()
    GenerateQRScreen(
        state = state.value,
        onBackClick = onBackClick,
        eventPublisher = {
            generateQRViewModel.setEvent(it);
        }
    )
}

@Composable
fun GenerateQRScreen(
    state: GenerateQRContract.GenerateQRState,
    onBackClick: () -> Unit,
    eventPublisher: (uiEvent: GenerateQRContract.GenerateQREvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
    ) {
        // Ikona za povratak ("X") u gornjem levom uglu
        Box(
            modifier = Modifier
                .align(Alignment.TopStart) // Poravnanje u gornji levi ugao
                .padding(16.dp) // Margina od ivice ekrana
                .clickable { onBackClick() } // Povratak na prethodni ekran
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close), // Ikona "X"
                contentDescription = "Close",
                tint = Color(0xFFED6825),
                modifier = Modifier.size(24.dp)
            )
        }

        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (!state.qrExpired) "Scan QR code on terminal" else "QR Code Expired",
                    style = poppinsBold.copy(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = if (!state.qrExpired) Color.White else Color.Red
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
                )

                if (!state.qrExpired) {
                    state.qrBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .size(250.dp)
                                .background(Color(0xFF333333))
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .background(
                                color = Color(0xFF333333),
                                shape = MaterialTheme.shapes.large
                            )
                            .clickable { eventPublisher(GenerateQRContract.GenerateQREvent.RegenerateQrCode) }
                            .height(48.dp)
                            .fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_refresh_24),
                            contentDescription = "Refresh QR Code",
                            tint = Color(0xFFED6825),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(24.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Regenerate QR",
                                style = poppinsBold.copy(
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                if (!state.qrExpired) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Expires in ${formatTime(state.timeRemaining)}",
                        style = poppinsRegular.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
                    )
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}